package com.restaurant.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UnitsOfMeasurement")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int unitID;

    private String unitName;

    private String unitAbbriviation;

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitAbbriviation() {
        return unitAbbriviation;
    }

    public void setUnitAbbriviation(String unitAbbriviation) {
        this.unitAbbriviation = unitAbbriviation;
    }

}
