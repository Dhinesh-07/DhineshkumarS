package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.Optional;

@Service
public class EmailForgetService {

    private final UserRepository userRepository;
    private final EmailForgetRepository emailForgetRepository;

    private final UserEncryptRepository userEncryptRepository;
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailForgetService.class);

    // Constructor-based dependency injection is preferred, but this is okay as well.
    @Autowired
    public EmailForgetService(UserRepository userRepository, EmailForgetRepository emailForgetRepository, UserEncryptRepository userEncryptRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.emailForgetRepository = emailForgetRepository;
        this.userEncryptRepository = userEncryptRepository;
        this.javaMailSender = javaMailSender;
    }
    @Value("${base.url}")
    private String baseUrl;
    public ResponseEntity<String> generatePasswordResetToken(String userEmail) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(userEmail);


        if (userOptional.isEmpty()) {
            logger.error("User not found for email: " + userEmail);
            return ResponseEntity.badRequest().body("User not found.");
        }

        UserEntity user = userOptional.get();
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);

        EmailForget emailForget = emailForgetRepository.findByUser(userOptional.get());
        if (emailForget == null) {

            emailForget = new EmailForget();
            emailForget.setUser(userOptional.get());
        }


        emailForget.setToken(token);
        emailForget.setExpiryDate(expiryDate);
        emailForgetRepository.saveAndFlush(emailForget); // Save or update the record.

        String resetLink = baseUrl+"/reset-password?token=" + token;
        String emailSubject = "Password Reset Request";
        String emailExpiryTime = "Your password reset link will expire in 5 minutes.";
        String emailContent = "Hi " + user.getName() + ",\n\nClick the link to reset your password: " + resetLink + "\n\n" + emailExpiryTime;

        sendEmail(user.getEmail(), emailSubject, emailContent);

        logger.info("Token generated for user with email: " + userEmail + ", Token: " + token);

        return ResponseEntity.ok("Password reset link sent to your email.");
    }


    public ResponseEntity<String> validatePasswordResetToken(String token) {
        logger.info("Validating password reset token: " + token);

        EmailForget emailForget = emailForgetRepository.findByToken(token);
        if (emailForget == null || emailForget.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.error("Invalid or expired token for token: " + token);
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Show the password reset page to the user
        logger.info("Password reset token is valid for token: " + token);
        return ResponseEntity.ok("Show the password reset page here.");
    }


    public ResponseEntity<String> updatePassword(String token, String password , String password1) {
        EmailForget emailForget = emailForgetRepository.findByToken(token);

        if (emailForget == null || emailForget.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.error("Invalid or expired token for token: " + token);
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        UserEntity user = emailForget.getUser();
        UserEncrypt userEncrypt = user.getUserEncrypt();

        userEncrypt.setPassword(password);
        userEncrypt.setPassword(password1);
        userEncrypt.encryptPassword(); // Encrypt the password again (optional)


        userEncrypt.setModify_time(getCurrentDateTime());

        userEncryptRepository.save(userEncrypt);

        // Delete the used token from the database
        emailForgetRepository.delete(emailForget);

        logger.info("Password updated for user with email: " + user.getEmail());

        return ResponseEntity.ok("Password updated successfully.");
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }
    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }


    public boolean isEmailRegistered(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.isPresent();
    }



}
