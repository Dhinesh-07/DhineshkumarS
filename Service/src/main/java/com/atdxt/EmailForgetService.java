package com.atdxt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailForgetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailForgetRepository emailForgetRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<String> generatePasswordResetToken(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail);
        if (user == null) {
            // User not found, return appropriate response
            return ResponseEntity.badRequest().body("User not found.");
        }

        // Generate a unique token and set an expiration time (e.g., 1 hour from now)
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        // Save the token in the database
        PasswordResetToken resetToken = new PasswordResetToken(token, expiryDate, user);
        emailForgetRepository.save(resetToken);

        // Send email with the password reset link
        String resetLink = "http://your-app-url/reset-password?token=" + token;
        String emailContent = "Click the link to reset your password: " + resetLink;
        sendEmail(userEmail, "Password Reset", emailContent);

        // Return success response
        return ResponseEntity.ok("Password reset link sent to your email.");
    }

    public ResponseEntity<String> validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            // Invalid or expired token, return appropriate response
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Show the password reset page to the user
        return ResponseEntity.ok("Show the password reset page here.");
    }

    public ResponseEntity<String> updatePassword(String token, String password) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            // Invalid or expired token, return appropriate response
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        // Update the user's password and save in the database
        User user = resetToken.getUser();
        user.setPassword(password);
        userRepository.save(user);

        // Delete the used token from the database
        tokenRepository.delete(resetToken);

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
