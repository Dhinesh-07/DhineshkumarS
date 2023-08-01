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

    private static final Logger logger = LoggerFactory.getLogger(EmailForgetController.class);

    @Autowired
    public EmailForgetController(EmailForgetService emailForgetService) {
        this.emailForgetService = emailForgetService;
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
            modelAndView.setViewName("error");
            modelAndView.addObject("error", response.getBody());
        }
        return modelAndView;
    }

    @PostMapping("/reset-password")
    public ModelAndView updatePassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       RedirectAttributes redirectAttributes) {
        logger.info("POST request received for /reset-password");
        ResponseEntity<String> response = emailForgetService.updatePassword(token, password);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("reset-password");
        modelAndView.addObject("message", response.getBody());
        modelAndView.addObject("token", token);
        return modelAndView;
    }
}
