package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
@RequestMapping(path="/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;

    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            logger.info("Fetching all users");
            List<UserEntity> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer id) {
        try {
            UserEntity user = userService.getUserById(id);
            logger.info("Fetching the user with ID: {}", id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error occurred while fetching user from the database", e);
            throw new CustomException("Error occurred while fetching user by ID from the database.");
        }
    }


    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity user) {
        try {
            if (!userService.isValidEmail(user.getEmail())) {
                throw new CustomException("Invalid email address");
            }

            if (userService.isEmailExists(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists,change Email address");
            }

            if (userService.isNameExists(user.getName())) {
                return ResponseEntity.badRequest().body("Name already exists,Change Name ");
            }

            if (user.getPhone_number() != null) {
                if (!userService.isValidPhoneNumber(user.getPhone_number())) {
                    throw new CustomException("Invalid phone number");
                }


            }
            userService.addUser(user);
            logger.info("User added successfully");
            return ResponseEntity.ok("User added successfully");
        } catch (CustomException customEx) {
            logger.error("CustomException occurred while adding users to the database", customEx);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customEx.getMessage());
        } catch (Exception ex) {
            logger.error("Error occurred while adding users to the database", ex);
            throw new CustomException("Error occurred while adding users to the database.");
        }
    }




    @PutMapping("/{id}")
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

    @PostMapping("/enpost")
    public ResponseEntity<String> createUser(@RequestBody UserEncrypt userEncrypt) {
        try {
            userService.saveUserEncrypt(userEncrypt);
            logger.info("User added successfully");
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            logger.error("Error occurred while adding user to the database", e);
            throw new CustomException("Error occurred while adding user to the database.");
        }
    }

    @GetMapping("/enget")
    public ResponseEntity<List<UserEncrypt>> getUserData() {
        try {
            List<UserEncrypt> userEncryptList = userService.getAllUserEncrypt();
            logger.info("Fetching all users");
            return ResponseEntity.ok(userEncryptList);
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }




}

