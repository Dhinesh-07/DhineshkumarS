package com.atdxt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserEntity user) {
        try {
            userRepository.save(user);
            logger.info("Adding data to the database");
            return ResponseEntity.ok("Saved Successfully");
        } catch (Exception e) {
            logger.error("Error:", e);
            throw e;
        }
    }
}
