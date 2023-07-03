package com.atdxt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserEntity2Repository userEntity2Repository;
    private final UserEncryptRepository userEncryptRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository, UserEntity2Repository userEntity2Repository, UserEncryptRepository userEncryptRepository) {

        this.userRepository = userRepository;
        this.userEntity2Repository = userEntity2Repository;
        this.userEncryptRepository = userEncryptRepository;

    }

    @GetMapping
    public @ResponseBody List<UserEntity> getAllUsers() {
        try {
            logger.info("Fetching all users");
            return userRepository.findAll();
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer id) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                logger.info("Fetching the id users");
                return ResponseEntity.ok(user);
            } else {
                throw new CustomException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching user from the database", e);
            throw new CustomException("Error occurred while fetching user by id from the database.");
        }
    }


    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = new UserEntity();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            userRepository.save(newUser);

            if(user.getUserEntity2() != null){
                UserEntity2 userEntity2 = new UserEntity2();
                userEntity2.setCity(user.getUserEntity2().getCity());
                userEntity2.setCountry(user.getUserEntity2().getCountry());

                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                userEntity2.setCreated_on(sdf1.format(new Date()));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                userEntity2.setModify_time(sdf.format(new Date()));

                userEntity2Repository.save(userEntity2);
                newUser.setUserEntity2(userEntity2);
            }

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newUser.setCreated_on(sdf1.format(new Date()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            newUser.setModify_time(sdf.format(new Date()));

            userRepository.save(newUser);
            logger.info("User added successfully");

            return ResponseEntity.ok("User added successfully");
        }
        catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while adding users from the database.");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Integer id, @RequestBody UserEntity user) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity existingUser = userOptional.get();
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());



                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                existingUser.setModify_time(sdf.format(new Date()));

                UserEntity2 userEntity2 = existingUser.getUserEntity2();
                if(userEntity2 != null){
                    userEntity2.setCity(user.getUserEntity2().getCity());
                    userEntity2.setCountry(user.getUserEntity2().getCountry());

                    userEntity2.setModify_time(sdf.format(new Date()));
                    userEntity2Repository.save(userEntity2);
                }

                userRepository.save(existingUser);
                logger.info("User updated successfully");

                return ResponseEntity.ok("User updated successfully");
            } else {
                throw new CustomException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID: " + id, e);
            throw new CustomException("Error occurred while updating user with ID: " + id);
        }
    }

    @PostMapping("/enpost")
    public ResponseEntity<String> createUser(@RequestBody UserEntity3 userEntity3) {
        try {
            UserEntity3 userEncrypt1 = new UserEntity3();
            userEncrypt1.setUsername(userEntity3.getUsername());
            userEncrypt1.setPassword(userEntity3.getPassword());
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            userEncrypt1.setCreated_on(sdf1.format(new Date()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            userEncrypt1.setModify_time(sdf.format(new Date()));

            userEncrypt1.encryptPassword();
            userEncryptRepository.save(userEncrypt1);

            logger.info("User added successfully");
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            logger.error("Error occurred while adding user to the database", e);
            throw new CustomException("Error occurred while adding user to the database.");
        }
    }



    @GetMapping("/enget")
    public List<UserEntity3> getUserData() {
        try {
            List<UserEntity3> userEncryptList = userEncryptRepository.findAll();
            for (UserEntity3 userEntity3 : userEncryptList) {
                userEntity3.decryptPassword();
            }
            logger.info("Fetching all users");
            return userEncryptList;
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }

}