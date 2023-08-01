package com.atdxt;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController

@Validated
@RequestMapping
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;

    }

    @GetMapping("/")
    public ModelAndView firstpage(ModelAndView model){
        model.setViewName("Home");
        return model;

    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public RedirectView processLogin(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     HttpServletRequest request) {

        try {
            request.login(username, password);
            return new RedirectView("/dashboard");
        } catch (ServletException e) {

            RedirectView redirectView = new RedirectView("/login");
            redirectView.addStaticAttribute("error", "Invalid username or password");
            return redirectView;
        }
    }

  @GetMapping("/users")
    public ModelAndView getAllUsers(ModelAndView model,  Principal principal) {
        try {
            logger.info("Fetching all users");
            String username = principal.getName();

            if (username.equals("admin")) {
                List<UserEntity> users = userService.getAllUsers();

                model.setViewName("users");
                model.addObject("users", users);
                return model;
            }
            else {

                UserEncrypt userEncrypt = userService.getUserByUsername(username);

                if (userEncrypt != null) {
                    List<UserEntity> users = new ArrayList<>();
                    users.add(userEncrypt.getUser());
                    model.setViewName("users");
                    model.addObject("users", users);
                    return model;
                } else {
                    throw new CustomException("User not found.");
                }
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }

   




    @GetMapping("/addUser")
    public ModelAndView getRegister() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        UserEntity addUser= new UserEntity();
        modelAndView.addObject("addUser", addUser);
        return modelAndView;
    }


    @PostMapping("/addUser")
    public ModelAndView addUser(@ModelAttribute("addUser") UserEntity addUser , @RequestParam("image") MultipartFile image) {

        try {
            ModelAndView modelAndView = new ModelAndView();

            if (addUser.getName() == null || addUser.getName().isEmpty()) {

                    String errorname="Please enter your name!";
    modelAndView.addObject("errorname",errorname);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }
            if (userService.isNameExists(addUser.getName())){
                String errornameexits="Name already exists!, Change the name";
                modelAndView.addObject("errornameexits",errornameexits);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }
            if (addUser.getEmail() == null || addUser.getEmail().isEmpty()) {
                String erroremail="Please enter your email!";
                modelAndView.addObject("erroremail",erroremail);
                modelAndView.setViewName("addUser");
                return modelAndView;

            }
            if (userService.isEmailExists(addUser.getEmail())) {
                String erroremailexits = "Email already exists!,change it";

                modelAndView.addObject("erroremailexits",erroremailexits);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }
            if (!userService.isValidEmail(addUser.getEmail())) {
                String erroremailinvalid = "Invalid email address!!!";
                modelAndView.addObject("erroremailinvalid",erroremailinvalid);
                modelAndView.setViewName("addUser");
                return modelAndView;


            }


            if (addUser.getPhone_number() != null && !addUser.getPhone_number().isEmpty() ) {


                if (!userService.isValidPhoneNumber(addUser.getPhone_number())) {
                    String errorphone = "Invalid phone number!!!";

                    modelAndView.addObject("errorphone",errorphone);
                    modelAndView.setViewName("addUser");
                    return modelAndView;

                }


            }
            if (addUser.getUserEncrypt().getUsername() == null || addUser.getUserEncrypt().getUsername().isEmpty()) {
                String errorusername = "Please enter your username!";
                modelAndView.addObject("errorusername", errorusername);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }


            if (userService.isUserNameExists(addUser.getUserEncrypt().getUsername())) {
                String errorusernameexists = "UserName already exists!,change it";

                modelAndView.addObject("errorusernameexists",errorusernameexists);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }
            if (addUser.getUserEncrypt().getPassword() == null || addUser.getUserEncrypt().getPassword().isEmpty()) {
                String Passwordempty = "Password must not be empty.";
                modelAndView.addObject("Passwordempty", Passwordempty);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }

            if (addUser.getUserEncrypt().getConfirmpassword() == null || addUser.getUserEncrypt().getConfirmpassword().isEmpty()) {
                String confirmPasswordempty = "Confirm Password must not be empty.";
                modelAndView.addObject("confirmPasswordempty", confirmPasswordempty);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }

            if (!addUser.getUserEncrypt().getConfirmpassword().equals(addUser.getUserEncrypt().getPassword())) {
                String passwordnotmatch = "Password and Confirm Password do not match.";
                modelAndView.addObject("passwordnotmatch", passwordnotmatch);
                modelAndView.setViewName("addUser");
                return modelAndView;
            }


            userService.addUser(addUser , image);



            logger.info("User added successfully");
            modelAndView.addObject("addUser", addUser);
            return new ModelAndView("redirect:/");



        }
        catch (CustomException customEx) {
            logger.error("CustomException occurred while adding users to the database", customEx);
            throw new CustomException("Error occurred while adding users to the database.");
        } catch (Exception ex) {
            logger.error("Error occurred while adding users to the database", ex);
            throw new CustomException("Error occurred while adding users to the database.");
        }
    }


    @PutMapping("update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserEntity user) {
        try {
            if (!userService.isValidEmail(user.getEmail())) {
                throw new CustomException("Invalid email address");
            }


            if (userService.isEmailExistsForOtherUser(user.getEmail(), id)) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            if (user.getPhone_number() != null) {
                    if (!userService.isValidPhoneNumber(user.getPhone_number())) {
                        return ResponseEntity.badRequest().body("Phone number is not valid");
                    }


                }

            userService.updateUser(id, user);
            logger.info("User updated successfully");
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID: " + id, e);
            throw new CustomException("Error occurred while updating user with ID: " + id);
        }
    }


    @GetMapping("/forgot-password")
    public ModelAndView showForgotPasswordPage() {
        ModelAndView modelAndView = new ModelAndView("forgot-password");
        return modelAndView;

    }


    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam("email") String email) {
        userService.processForgotPassword(email);

    }
    @GetMapping("/forgot-password/reset")
    public ModelAndView showResetPasswordPage(@RequestParam String token) {
        ModelAndView modelAndView = new ModelAndView("resetpwd");
        return modelAndView;
    }

    @PostMapping("/forgot-password/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }

}


