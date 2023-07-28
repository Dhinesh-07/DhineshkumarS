package com.atdxt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final S3Client s3Client;

    private final UserEntity2Repository userEntity2Repository;
    private final UserEncryptRepository userEncryptRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, UserEntity2Repository userEntity2Repository, UserEncryptRepository userEncryptRepository) {
        this.userRepository = userRepository;
        this.userEntity2Repository = userEntity2Repository;
        this.userEncryptRepository = userEncryptRepository;
        this.s3Client = S3Client.builder().region(Region.US_EAST_1).build();
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

    public void addUser(UserEntity addUser, MultipartFile image) {
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

            UserEntity2 userEntity2 = new UserEntity2();

            if (!image.isEmpty()) {
                String imageUrl = uploadImageToS3(image);
                userEntity2.setImageUrl(imageUrl);

                if (addUser.getUserEntity2() != null) {
                    String city = addUser.getUserEntity2().getCity();
                    String country = addUser.getUserEntity2().getCountry();
                    userEntity2.setCity(city);
                    userEntity2.setCountry(country);
                }

                userEntity2.setCreated_on(getCurrentDate());
                userEntity2.setModify_time(getCurrentDateTime());
                userEntity2Repository.save(userEntity2);
                newUser.setUserEntity2(userEntity2);
                userEntity2.setUser(newUser);

            } else {

                userEntity2.setImageUrl("");

                if (addUser.getUserEntity2() != null) {
                    String city = addUser.getUserEntity2().getCity();
                    String country = addUser.getUserEntity2().getCountry();
                    userEntity2.setCity(city);
                    userEntity2.setCountry(country);
                }

                userEntity2.setCreated_on(getCurrentDate());
                userEntity2.setModify_time(getCurrentDateTime());
                userEntity2Repository.save(userEntity2);
                newUser.setUserEntity2(userEntity2);

                userEntity2.setUser(newUser);
                System.out.println(newUser);
            }



            UserEncrypt userEncrypt = new UserEncrypt();
            if (isUserNameExists(addUser.getUserEncrypt().getUsername())) {
                throw new IllegalArgumentException("userName already exists, change email address");
            }

            userEncrypt.setUsername(addUser.getUserEncrypt().getUsername());

            if (addUser.getUserEncrypt().getPassword() ==null){
                throw new IllegalArgumentException("Password must not be empty.");
            }
            if (addUser.getUserEncrypt().getConfirmpassword() ==null){
                throw new IllegalArgumentException("confirm");
            }
            if (!addUser.getUserEncrypt().getConfirmpassword().equals(addUser.getUserEncrypt().getPassword())) {
                throw new IllegalArgumentException("Password and Confirm Password do not match.");
            }

            userEncrypt.setPassword(addUser.getUserEncrypt().getPassword());
            userEncrypt.setConfirmpassword(addUser.getUserEncrypt().getConfirmpassword());
            userEncrypt.encryptPassword();
            userEncrypt.setCreated_on(getCurrentDate());
            userEncrypt.setModify_time(getCurrentDateTime());
            userEncryptRepository.save(userEncrypt);


            newUser.setUserEncrypt(userEncrypt);
            userEncrypt.setUser(newUser);

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
            userEncrypt.setCreated_on(getCurrentDate());
            userEncrypt.setModify_time(getCurrentDateTime());
            userEncryptRepository.save(userEncrypt);
        } catch (Exception e) {
            logger.error("Error occurred while saving userEncrypt to the database", e);
            throw new CustomException("Error occurred while saving userEncrypt to the database.");
        }
    }

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
    public boolean isUserNameExists(String email) {
        return userEncryptRepository.existsByUserName(email);
    }

    public boolean isEmailExistsForOtherUser(String email, Integer id) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity existingUser = userOptional.get();
            return !existingUser.getId().equals(id);
        }
        return false;
    }

    public UserEncrypt getUserByUsername(String username) {
        System.out.println("Getting user details for username: " + username);

        System.out.println("User details retrieved: " + userEncryptRepository.findByUsername(username)
                .orElse(null));
        return userEncryptRepository.findByUsername(username)
                .orElse(null);
    }










    public boolean isPasswordConfirmed(String password, String confirm_password) {
        return password != null && confirm_password != null && password.equals(confirm_password);
    }


    @Value("${aws.accessKeyId}")
    private String awsAccessKeyId;
    {
        System.out.println(awsAccessKeyId);
    }


    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.Bucket}")
    private String awsS3BucketName;


    public String uploadImageToS3(MultipartFile image) throws IOException {
        try {
           /* String bucketName = "localmysql-s3";*/
            String key = "images/" + image.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsS3BucketName)
                    .key(key)
                    .contentType(image.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));

            return "https://"+awsS3BucketName+".s3.amazonaws.com/" + key;
        } catch (S3Exception e) {
            e.printStackTrace();
            // Handle the exception accordingly
            throw e;
        }
    }


}
