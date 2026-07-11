package com.restaurant.inventory.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PurchaseOrder")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderID;

    private LocalDate orderDate;

    @ManyToOne
    @JoinColumn(name = "itemID", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = true)
    private Supplier supplier;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "unitID", nullable = false)
    private Unit  unit;

    private String orderStatus = "pending";

    private String cancelationReason;

    public String getCancelationReason() {
        return cancelationReason;
    }

    public void setCancelationReason(String cancelationReason) {
        this.cancelationReason = cancelationReason;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
