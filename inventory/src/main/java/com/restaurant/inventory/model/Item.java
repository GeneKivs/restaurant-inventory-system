package com.restaurant.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//data class that represents the item in the inventory
// this class is used to define the item in the inventory
@Entity
@Table(name = "Items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemID;

    private String ItemName;

    //foreign key
    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private Categories category;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "unitID", nullable = false)
    private Unit unit;

    private double reorderlevel;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getReorderlevel() {
        return reorderlevel;
    }

    public void setReorderlevel(double reorderlevel) {
        this.reorderlevel = reorderlevel;
    }

}
