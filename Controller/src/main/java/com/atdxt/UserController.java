package com.atdxt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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


    @GetMapping("/users")
    public ModelAndView getAllUsers(ModelAndView model) {
        try {
            logger.info("Fetching all users");
            List<UserEntity> users = userService.getAllUsers();
            for (UserEntity user :users) {
                UserEncrypt userEncrypt = user.getUserEncrypt();
                userService.decryptUserEncrypt(userEncrypt);
            }
                model.setViewName("users");
                model.addObject("users", users);


            return model; // Update the view template path
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }


    @GetMapping("/{id}")
    public ModelAndView getUserById(ModelAndView model ,@PathVariable("id") Integer id) {
        try {
            logger.info("Fetching all users");
            UserEntity user = userService.getUserById(id);
            model.setViewName("users");
            model.addObject("users",user);


            return model; // Update the view template path
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }
    @GetMapping("/enget")
    public ModelAndView getUserData(ModelAndView model) {
        try {
            List<UserEncrypt> userEncryptList = userService.getAllUserEncrypt();
            for (UserEncrypt userEncrypt : userEncryptList){
    userService.decryptUserEncrypt(userEncrypt);
            }

            logger.info("Fetching all users");
            model.setViewName("users1");
            model.addObject("users1",userEncryptList);

            return model;
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

/*
    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute("addUser") UserEntity addUser, RedirectAttributes redirectAttributes) {
        RedirectView redirectView = new RedirectView();
        try {
            if (addUser.getName() == null || addUser.getName().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorname", "Please enter your name!");
                redirectView.setUrl("/addUser");
                return redirectView;
            }
            if (userService.isNameExists(addUser.getName())){
                redirectAttributes.addFlashAttribute("errornameexits", "Name already exists!, Change the name");
                redirectView.setUrl("/addUser");
                return redirectView;

            }
            if (addUser.getEmail() == null || addUser.getEmail().isEmpty()) {
                redirectAttributes.addFlashAttribute("erroremail", "Please enter email!");
                redirectView.setUrl("/addUser");
                return redirectView;
            }
            if (userService.isEmailExists(addUser.getEmail())) {
                redirectAttributes.addFlashAttribute("erroremailexits", "Email already exists!,change it");
                redirectView.setUrl("/addUser");
                return redirectView;
            }
            if (!userService.isValidEmail(addUser.getEmail())) {
                redirectAttributes.addFlashAttribute("erroremailinvalid", "Invalid email address!!!");
                redirectView.setUrl("/addUser");
                return redirectView;
            }


            if (addUser.getPhone_number() != null && !addUser.getPhone_number().isEmpty() ) {


                if (!userService.isValidPhoneNumber(addUser.getPhone_number())) {
                    redirectAttributes.addFlashAttribute("errorphone", "Invalid phone number!!!");
                    redirectView.setUrl("/addUser");
                    return redirectView;
                }


            }


            userService.addUser(addUser);
            logger.info("User added successfully");

            redirectView.setUrl("/login");
            return redirectView;

        } catch (CustomException customEx) {
            logger.error("CustomException occurred while adding users to the database", customEx);
            throw new CustomException("Error occurred while adding users to the database.");
        } catch (Exception ex) {
            logger.error("Error occurred while adding users to the database", ex);
            throw new CustomException("Error occurred while adding users to the database.");
        }
    }
*/


    @PostMapping("/addUser")
    public ModelAndView addUser(@ModelAttribute("addUser") UserEntity addUser) {

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


            userService.addUser(addUser);
            logger.info("User added successfully");
            modelAndView.addObject("addUser", addUser);
            modelAndView.setViewName("Home");
            return modelAndView;


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

   /* @PostMapping("/enpost")
    public ResponseEntity<String> createUser(@RequestBody UserEncrypt userEncrypt) {
        try {
            userService.saveUserEncrypt(userEncrypt);
            logger.info("User added successfully");
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            logger.error("Error occurred while adding user to the database", e);
            throw new CustomException("Error occurred while adding user to the database.");
        }
    }*/





}


