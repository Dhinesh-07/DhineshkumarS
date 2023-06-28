package com.atdxt;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

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


    @GetMapping(path="all/{id}")
    public UserEntity getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user){
        userService.createUser(user);
        return ResponseEntity.ok("Saved successfully");
    }

    @PutMapping(path="update/{id}")
    public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }


}
