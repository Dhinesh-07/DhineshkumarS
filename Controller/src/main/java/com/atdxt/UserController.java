package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            logger.info("Getting all data from the database");
            List<UserEntity> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }

    @GetMapping(path ="all/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        try {
            logger.info("Getting data from the database for ID: " + id);
            UserEntity user = userService.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserEntity user) {
        try {
            userService.addUser(user);
            logger.info("Adding data to the database");
            return ResponseEntity.ok("Saved Successfully");
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }

    @PutMapping(path ="/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
        try {
            boolean isUpdated = userService.updateUser(id, updatedUser);
            if (isUpdated) {
                logger.info("Updating user with ID: " + id);
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }
}
