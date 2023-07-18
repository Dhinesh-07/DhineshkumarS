package com.atdxt;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    private final UserEntity2Repository userEntity2Repository;
    private final UserEncryptRepository userEncryptRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, UserEntity2Repository userEntity2Repository, UserEncryptRepository userEncryptRepository) {
        this.userRepository = userRepository;
        this.userEntity2Repository = userEntity2Repository;
        this.userEncryptRepository = userEncryptRepository;
    }

    public List<UserEntity> getAllUsers() {
        try {
            logger.info("Fetching all users");
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while fetching users from the database", e);
            throw new CustomException("Error occurred while fetching users from the database.");
        }
    }

    public UserEntity getUserById(Integer id) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                logger.info("Fetching the user with ID: {}", id);
                return user;
            } else {
                throw new CustomException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching user from the database", e);
            throw new CustomException("Error occurred while fetching user by ID from the database.");
        }
    }

    public List<UserEncrypt> getAllUserEncrypt() {
        try {
            logger.info("Fetching all userEncrypt");
            return userEncryptRepository.findAll();
        } catch (Exception e) {
            logger.error("Error occurred while fetching userEncrypts from the database", e);
            throw new CustomException("Error occurred while fetching userEncrypts from the database.");
        }
    }

    public void addUser(UserEntity addUser) {
        try {

            if (addUser.getName() == null || addUser.getName().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (isEmailExists(addUser.getEmail())) {
                throw new IllegalArgumentException("Email already exists, change email address");
            }
            if (isNameExists(addUser.getName())) {
                throw new IllegalArgumentException("Name already exists, change name");
            }
            if (!isValidEmail(addUser.getEmail())) {
                throw new CustomException("Invalid email address");
            }
            UserEntity newUser = new UserEntity();
            newUser.setName(addUser.getName());
            newUser.setEmail(addUser.getEmail());
            newUser.setAge(addUser.getAge());
            newUser.setCreated_on(getCurrentDate());
            newUser.setModify_time(getCurrentDateTime());

            if (addUser.getPhone_number() != null) {
                if (!isValidPhoneNumber(addUser.getPhone_number())) {
                    throw new CustomException("Invalid phone number");
                }

                newUser.setPhone_number(addUser.getPhone_number());
            }
            userRepository.save(newUser);

            if (addUser.getUserEntity2() != null) {
                UserEntity2 userEntity2 = new UserEntity2();
                userEntity2.setCity(addUser.getUserEntity2().getCity());
                userEntity2.setCountry(addUser.getUserEntity2().getCountry());
                userEntity2.setCreated_on(getCurrentDate());
                userEntity2.setModify_time(getCurrentDateTime());
                userEntity2Repository.save(userEntity2);
                newUser.setUserEntity2(userEntity2);
            }

            UserEncrypt userEncrypt = new UserEncrypt();
            userEncrypt.setUsername(addUser.getUserEncrypt().getUsername());
            userEncrypt.setPassword(addUser.getUserEncrypt().getPassword());
            userEncrypt.setConfirmpassword(addUser.getUserEncrypt().getConfirmpassword());
            userEncrypt.encryptPassword();
            userEncrypt.setCreated_on(getCurrentDate());
            userEncrypt.setModify_time(getCurrentDateTime());
            userEncryptRepository.save(userEncrypt);
            newUser.setUserEncrypt(userEncrypt);

            userRepository.save(newUser);
            logger.info("User added successfully");
        } catch (Exception e) {
            logger.error("Error occurred while adding users to the database", e);
            throw new CustomException("Error occurred while adding users to the database.");
        }
    }




    public void updateUser(Integer id, UserEntity user) {
        try {
            Optional<UserEntity> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                UserEntity existingUser = userOptional.get();



                if (!isValidEmail(user.getEmail())) {
                    throw new CustomException("Invalid email address");
                }
                if (isEmailExistsForOtherUser(user.getEmail(), id)) {
                    throw new CustomException("Email already exists");
                }
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setAge(user.getAge());
                if (user.getPhone_number() != null) {

                    if (!isValidPhoneNumber(user.getPhone_number())) {
                        throw new CustomException("Invalid phone number");
                    }


                    existingUser.setPhone_number(user.getPhone_number());
                }
                existingUser.setModify_time(getCurrentDateTime());

                UserEntity2 userEntity2 = existingUser.getUserEntity2();
                if (userEntity2 != null) {
                    userEntity2.setCity(user.getUserEntity2().getCity());
                    userEntity2.setCountry(user.getUserEntity2().getCountry());
                    userEntity2.setModify_time(getCurrentDateTime());
                    userEntity2Repository.save(userEntity2);
                }

                userRepository.save(existingUser);
                logger.info("User updated successfully");
            } else {
                throw new CustomException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating user with ID: " + id, e);
            throw new CustomException("Error occurred while updating user with ID: " + id);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf1.format(new Date());
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

 public void saveUserEncrypt(UserEncrypt userEncrypt) {
        try {
            userEncrypt.setUsername(userEncrypt.getUsername());
            userEncrypt.encryptPassword();
//            userEncrypt.encryptconfirmPassword();
            userEncrypt.setCreated_on(getCurrentDate());
            userEncrypt.setModify_time(getCurrentDateTime());
            userEncryptRepository.save(userEncrypt);
        } catch (Exception e) {
            logger.error("Error occurred while saving userEncrypt to the database", e);
            throw new CustomException("Error occurred while saving userEncrypt to the database.");
        }
    }

    /*public void decryptUserEncrypt(UserEncrypt userEncrypt) {
        try {
            userEncrypt.decryptPassword();

        } catch (Exception e) {
            logger.error("Error occurred while decrypting userEncrypt password", e);
            throw new CustomException("Error occurred while decrypting userEncrypt password.");
        }
    }*/

    public boolean isValidEmail(String email) {
        return email != null && email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}");
    }
    public boolean isValidPhoneNumber(String phone_number) {
        return phone_number != null && phone_number.matches("\\d{10}");
    }



    public boolean isNameExists(String name) {
        return userRepository.existsByName(name);
    }

    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }



    public boolean isEmailExistsForOtherUser(String email, Integer id) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity existingUser = userOptional.get();
            return !existingUser.getId().equals(id);
        }
        return false;
    }


}
