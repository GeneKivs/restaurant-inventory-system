package com.restaurant.inventory.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController{

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("code", statusCode);

                    switch(statusCode){
                        case 400: return "400";
                        case 403: return "403";
                        case 404: return "404";
                        case 405: return "405";
                        case 500: return "500";
                        default: return "error";

                        
                        

                    }
        }

        return "error";
    }

}
