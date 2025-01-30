package com.restaurant.inventory.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "Issueitems")
public class IsssueItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueID;

    private double issuedQuantity;

    private LocalDate issueDate;

    @ManyToOne
    @JoinColumn(name = "unitID", nullable = false)
    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @ManyToOne
    @JoinColumn(name =  "itemID" , nullable = false)
    private Item item;

    public Long getIssueID() {
        return issueID;
    }

    public void setIssueID(Long issueID) {
        this.issueID = issueID;
    }

    public double getIssuedQuantity() {
        return issuedQuantity;
    }

    public void setIssuedQuantity(double issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public LocalDate getIssuDate() {
        return issueDate;
    }

    public void setIssuDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
