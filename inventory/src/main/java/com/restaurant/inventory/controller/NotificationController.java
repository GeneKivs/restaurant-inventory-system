package com.restaurant.inventory.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.restaurant.inventory.service.NotificationService;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    

     @PostMapping("/notifications/markAsRead/{notificationID}")
     public String markNotificationAsRead(@PathVariable Long notificationID){
        notificationService.maskRead(notificationID);
        return "redirect:/dashboard";
     }

}
