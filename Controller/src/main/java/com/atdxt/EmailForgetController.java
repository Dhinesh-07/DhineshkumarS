package com.atdxt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmailForgetController {

    private final EmailForgetService emailForgetService;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(EmailForgetController.class);

    @Autowired
    public EmailForgetController(EmailForgetService emailForgetService, UserService userService) {
        this.emailForgetService = emailForgetService;
        this.userService=userService;
    }

    @GetMapping("/forgot-password")
    public ModelAndView showForgotPasswordPage() {
        logger.info("GET request received for /forgot-password");
        ModelAndView modelAndView = new ModelAndView("forgot-password");
        return modelAndView;
    }

    @PostMapping("/forgot-password")
    public ModelAndView forgotPassword(@RequestParam("email") String userEmail, RedirectAttributes redirectAttributes) {
        logger.info("POST request received for /forgot-password");
        if (userEmail == null || userEmail.isEmpty()) {
            ModelAndView errorModelAndView = new ModelAndView("forgot-password");
            errorModelAndView.addObject("errorresetemail", "Email cannot be empty.");
            return errorModelAndView;
        }else if (!userService.isValidEmail(userEmail)) {
            ModelAndView errorModelAndView = new ModelAndView("forgot-password");
            errorModelAndView.addObject("invalidemailerror", "Invalid email address");
            return errorModelAndView;
        }

        if (!emailForgetService.isEmailRegistered(userEmail)) {
            ModelAndView errorModelAndView = new ModelAndView("forgot-password");
            errorModelAndView.addObject("emailnotregisterederror", "Email is not registered. Kindly register.");
            return errorModelAndView;
        }
        ResponseEntity<String> response = emailForgetService.generatePasswordResetToken(userEmail);
        ModelAndView modelAndView = new ModelAndView("forgot-password");
        modelAndView.addObject("message", response.getBody());
        return modelAndView;
    }

    @GetMapping("/reset-password")
    public ModelAndView showResetPasswordPage(@RequestParam("token") String token) {
        logger.info("GET request received for /reset-password");
        ResponseEntity<String> response = emailForgetService.validatePasswordResetToken(token);
        ModelAndView modelAndView = new ModelAndView();
        if (response.getStatusCode().is2xxSuccessful()) {
            modelAndView.setViewName("reset-password");
            modelAndView.addObject("token", token);
        } else {
            String tokenError = "The link has expired or is invalid.";
            modelAndView.setViewName("error");
            modelAndView.addObject("tokenError", tokenError);
        }
        return modelAndView;
    }

    @PostMapping("/reset-password")
    public ModelAndView updatePassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("password1") String password1,
                                       RedirectAttributes redirectAttributes) {
        logger.info("POST request received for /reset-password");
        ModelAndView modelAndView = new ModelAndView();
        if (password == null || password.trim().isEmpty() || password1 == null || password1.trim().isEmpty()) {
            String errorresetpassword = "Password cannot be empty.";
            modelAndView.addObject("errorresetpassword", errorresetpassword);
            modelAndView.setViewName("reset-password");
            modelAndView.addObject("token", token);
            return modelAndView;
        }

        // Check if the passwords match
        if (!password.equals(password1)) {
            String errorpassword = "Passwords do not match.";
            modelAndView.addObject("errorpassword", errorpassword);
            modelAndView.setViewName("reset-password");
            return modelAndView;
        }

        ResponseEntity<String> response = emailForgetService.updatePassword(token, password,password1);
        modelAndView.setViewName("reset-password");
        modelAndView.addObject("message", response.getBody());
        modelAndView.addObject("token", token);
        return modelAndView;
    }
}
