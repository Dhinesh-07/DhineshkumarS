package com.atdxt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserEntity2Repository userEntity2Repository;

    @Autowired
    public UserService(UserRepository userRepository, UserEntity2Repository userEntity2Repository) {
        this.userRepository = userRepository;
        this.userEntity2Repository = userEntity2Repository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) {
        user.setDate(LocalDateTime.now());
        user.setModifiedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    public UserEntity updateUser(Integer id, UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            UserEntity updatedUser = existingUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setModifiedDate(LocalDateTime.now());
            return userRepository.save(updatedUser);
        }
        return null;
    }

    public List<UserEntity> getAllUsersWithDetails() {
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            UserEntity2 user2 = user.getUserEntity2();
            if (user2 != null) {
                user2.setUser(null);
            }
        }
        return users;
    }
}
