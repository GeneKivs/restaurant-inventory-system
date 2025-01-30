package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.repository.UnitRepository;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public void defineUnit(Unit unit){
        unitRepository.save(unit);
    }
     
    public List<Unit> getAllUnits(){
        return unitRepository.findAll();
    }

    public Unit findById(int unitID) {
        return unitRepository.findById(unitID)
                .orElseThrow(() -> new RuntimeException("Unit not found with ID: " + unitID));
    }

    public void updateUnit(Unit unit) {
        unitRepository.save(unit);
    }
    
    public void deleteUnitById(int unitID) {
        unitRepository.deleteById(unitID);
    }
    
   
}
