package com.atdxt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public UserEntity getUserById(Integer id) {
        try {
            Optional<UserEntity> user = userRepository.findById(id);
            return user.orElse(null);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addUser(UserEntity user) {
        try {
            user.setDate(LocalDateTime.now());
            user.setModifiedDate(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean updateUser(Integer id, UserEntity updatedUser) {
        try {
            Optional<UserEntity> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                UserEntity user = existingUser.get();
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setModifiedDate(LocalDateTime.now());
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
