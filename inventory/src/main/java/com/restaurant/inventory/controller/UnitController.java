package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.UnitService;

@Controller
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping("/inventory/unit")
    public String showUnitform(Model model){
        Unit unit = new Unit();
        model.addAttribute("unit", unit);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);
        return "unitform";
    }

    @PostMapping("/saveUnit")
    public String saveUnit(@ModelAttribute("unit") Unit unit){
        System.out.println(unit);
        unitService.defineUnit(unit);

        return "redirect:/inventory/unit";
    }
    // Show the edit form for a specific unit
    @GetMapping("/edit/{unitID}")
    public String showEditForm(@PathVariable("unitID") int unitID, Model model) {
        // Fetch the unit to be edited
        Unit unit = unitService.findById(unitID);
        model.addAttribute("unit", unit);
        return "unitformupdate"; // Name of the Thymeleaf template for editing the unit
    }

    // Update the unit details
    @PostMapping("/updateUnit")
    public String updateUnit(@ModelAttribute("unit") Unit unit) {
        unitService.updateUnit(unit);
        return "redirect:/inventory/unit";
    }

    @PostMapping("/delete/{unitID}")
public String deleteUnit(@PathVariable("unitID") int unitID) {
    unitService.deleteUnitById(unitID);
    return "redirect:/inventory/unit"; // Redirect to the main page after deletion
}


    

}
