package com.atdxt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;
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

    public ResponseEntity<String> generatePasswordResetToken(String userEmail) {
        Optional<UserEntity> user = userRepository.findByEmail(userEmail);
        // The `user` variable will never be null due to `Optional`, but it could be empty.
        if (user.isEmpty()) { // Better use `isEmpty()` instead of `== null`.
            logger.error("User not found for email: " + userEmail);
            return ResponseEntity.badRequest().body("User not found.");
        }

        // Generating a random UUID is a good approach for token generation.
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        // Save the token in the database
        EmailForget emailForget = new EmailForget();
        emailForget.setToken(token);
        emailForget.setExpiryDate(expiryDate);
        emailForget.setUser(user.get());
        emailForgetRepository.save(emailForget);

        // Sending an email with the token link should be an async operation for better performance.
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        String emailContent = "Click the link to reset your password: " + resetLink;
        sendEmail(userEmail, "Password Reset", emailContent);

        logger.info("Token generated for user with email: " + userEmail + ", Token: " + token);

        // Return success response
        return ResponseEntity.ok("Password reset link sent to your email.");
    }

    public ResponseEntity<String> validatePasswordResetToken(String token) {
        EmailForget emailForget = emailForgetRepository.findByToken(token);
        // Use `isEmpty()` to check if the optional is empty.
        if (emailForget == null || emailForget.getExpiryDate().isBefore(LocalDateTime.now())) {
            // Invalid or expired token, return appropriate response
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Show the password reset page to the user
        return ResponseEntity.ok("Show the password reset page here.");
    }

    public ResponseEntity<String> updatePassword(String token, String password) {
        EmailForget emailForget = emailForgetRepository.findByToken(token);
        // Use `isEmpty()` to check if the optional is empty.
        if (emailForget == null || emailForget.getExpiryDate().isBefore(LocalDateTime.now())) {
            // Invalid or expired token, return appropriate response
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        UserEntity user = emailForget.getUser();
        UserEncrypt userEncrypt = user.getUserEncrypt();

        // Update the user's encrypted password and save it in the database
        userEncrypt.setPassword(password);
        userEncrypt.encryptPassword(); // Encrypt the password again (optional)
        userEncryptRepository.save(userEncrypt);

        // Delete the used token from the database
        emailForgetRepository.delete(emailForget);

        // Return success response
        return ResponseEntity.ok("Password updated successfully.");
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        javaMailSender.send(message);
    }
}
