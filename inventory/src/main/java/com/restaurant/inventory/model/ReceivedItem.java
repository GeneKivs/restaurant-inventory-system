package com.restaurant.inventory.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "receivedinventory")
public class ReceivedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivedID;

    private LocalDate receivedDate;

    private LocalDate editedDate;

    public LocalDate getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(LocalDate editedDate) {
        this.editedDate = editedDate;
    }

    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "itemID",nullable = false)
    private Item item;

    private double receiveedquantity;

    private double expiredstock;

    public double getExpiredstock() {
        return expiredstock;
    }

    public void setExpiredstock(double expiredstock) {
        this.expiredstock = expiredstock;
    }

    private double damagequantity = 0;

    public double getDamagequantity() {
        return damagequantity;
    }

    public void setDamagequantity(double damagequantity) {
        this.damagequantity = damagequantity;
    }

    private double remainingQuantity;

    public double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(double remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    private double pendingquantity = 0;

    public double getPendingquantity() {
        return pendingquantity;
    }

    public void setPendingquantity(double pendingquantity) {
        this.pendingquantity = pendingquantity;
    }

    private String receivedStatus;


    public String getReceivedStatus() {
        return receivedStatus;
    }

    public void setReceivedStatus(String receivedStatus) {
        this.receivedStatus = receivedStatus;
    }

    @ManyToOne
    @JoinColumn(name = "unitID",nullable = false)
    private Unit unit;

   

    @Column(nullable = true)
    private LocalDate expiryDate;

    public Long getReceivedID() {
        return receivedID;
    }

    public void setReceivedID(Long receivedID) {
        this.receivedID = receivedID;
    }

    public LocalDate getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDate receivedDate) {
        this.receivedDate = receivedDate;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getReceiveedquantity() {
        return receiveedquantity;
    }

    public void setReceiveedquantity(double receiveedquantity) {
        this.receiveedquantity = receiveedquantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }


}
