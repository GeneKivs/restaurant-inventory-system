package com.restaurant.inventory.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.model.SupplierLinkage;
import com.restaurant.inventory.repository.SupplierLinkageRepository;

@Service
public class SupplierLinkageService {

    /*@Autowired
    private SupplierLinkageRepository supplierLinkageRepository;*/

    private final SupplierLinkageRepository supplierLinkageRepository;

    public SupplierLinkageService(SupplierLinkageRepository supplierLinkageRepository){
        this.supplierLinkageRepository = supplierLinkageRepository;
    }

    public void defineLink(SupplierLinkage supplierLinkage){
        supplierLinkageRepository.save(supplierLinkage);
    }

    public List<SupplierLinkage> getAllLinks(){
        return supplierLinkageRepository.findAll();
    }

    public SupplierLinkage findLinkById(int supplierLinkID){
        return supplierLinkageRepository.findById(supplierLinkID)
        .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierLinkID));
    }

    public void updateLink(SupplierLinkage supplierLinkage){
        supplierLinkageRepository.save(supplierLinkage);
    }

    public void deleteLinkById(int supplierLinkID){
        supplierLinkageRepository.deleteById(supplierLinkID);
    }

    public List<Supplier> getSuppliersByItemID(int itemID){
        return supplierLinkageRepository.findSuppliersByitemId(itemID);
    }

}
