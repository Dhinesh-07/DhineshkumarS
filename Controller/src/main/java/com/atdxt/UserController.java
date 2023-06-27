package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<UserEntity> getAllUsers() {
        try {
            logger.info("Getting all data from the database");
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }
    @GetMapping(path = "/all/{id}")
    public @ResponseBody
    UserEntity getUserById(@PathVariable Integer id) {
        try {
            logger.info("Getting data from the database for ID: " + id);
            Optional<UserEntity> user = userRepository.findById(id);
            return user.orElse(null);
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserEntity user) {
        try {

            user.setDate(LocalDateTime.now());

           userRepository.save(user);
            logger.info("Adding data to the database");
            return ResponseEntity.ok("Saved Successfully");
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
        try {
            Optional<UserEntity> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                UserEntity user = existingUser.get();
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setModifiedDate(LocalDateTime.now()); // Set the modified date to the current date and time

                userRepository.save(user);
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
