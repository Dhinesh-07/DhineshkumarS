package com.atdxt.JDBCConnection.Controller;

import com.atdxt.JDBCConnection.Model.User;
import com.atdxt.JDBCConnection.Repository.UserRepository;
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



    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {

        return userRepository.findAll();
    }

        @PostMapping("/add")
        public ResponseEntity <String> addNewUser(@RequestBody User user ) {

            userRepository.save(user);
            return ResponseEntity.ok("Saved Successfully");
        }









}

