package com.restaurant.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.service.ForgotPsswordService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private ForgotPsswordService forgotPsswordService;


    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm(){
        return "forgotpassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String email,  RedirectAttributes redirectAttributes){
        boolean success = forgotPsswordService.resetPassword(email);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "A new password has been sent to your email.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No account found with that email.");
        }

        return "redirect:/forgotPassword";
    }

}
