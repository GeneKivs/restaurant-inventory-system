package com.restaurant.inventory.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.repository.SupplierRepository;

@Service
public class SupplierService {

    //@Autowired
    //private SupplierRepository supplierRepository;

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }



    public void defineSupplier(Supplier supplier){
        supplierRepository.save(supplier);
    }

    public List<Supplier> getALLsuppliers(){
        return supplierRepository.findAll();
    }

    public Supplier findSupplierById(int supplierID){
        return supplierRepository.findById(supplierID)
                .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierID));
    }

    public void updateSupplier(Supplier supplier){
        supplierRepository.save(supplier);
    }

    public void deleteSupplierById(int supplierID){
        supplierRepository.deleteById(supplierID);
    }

}
