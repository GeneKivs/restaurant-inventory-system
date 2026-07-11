package com.restaurant.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoverPageController {

    @GetMapping("/")
    public String showcoverpage(){
        return "coverpage";
    }
}
