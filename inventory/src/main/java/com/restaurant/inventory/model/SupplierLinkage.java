package com.restaurant.inventory.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SupplierItemLink")
public class SupplierLinkage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierLinkID;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "itemID", nullable = false)
    private Item item;

    public int getSupplierLinkID() {
        return supplierLinkID;
    }

    public void setSupplierLinkID(int supplierLinkID) {
        this.supplierLinkID = supplierLinkID;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


}
