package com.restaurant.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingController {

    @GetMapping("/setting")
    public String showSettingform(){
        return "settingform";
    }

    @GetMapping("/profilesetting")
    public String showPriofileSettingform(){
        return "profilesettingform";
    }

}
