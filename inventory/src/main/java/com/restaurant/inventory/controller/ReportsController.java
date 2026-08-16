package com.restaurant.inventory.controller;

import java.time.LocalDate;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurant.inventory.model.IsssueItem;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.ReceivedItem;
import com.restaurant.inventory.model.Supplier;

import com.restaurant.inventory.service.IssueItemService;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.ReceivedItemService;
import com.restaurant.inventory.service.SupplierService;

@Controller
public class ReportsController {

    /*@Autowired
    private ItemService itemService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ReceivedItemService receivedItemService;

    @Autowired
    private IssueItemService issueItemService;*/

    private final ItemService itemService;
    private final SupplierService supplierService;
    private final ReceivedItemService receivedItemService;
    private final IssueItemService issueItemService;

    public ReportsController(IssueItemService issueItemService, ItemService itemService, ReceivedItemService receivedItemService, SupplierService supplierService){
        this.itemService = itemService;
        this.supplierService = supplierService;
        this.receivedItemService = receivedItemService;
        this.issueItemService = issueItemService;

    }



    @GetMapping("/report")
    public String showReportsForm(){
        return "reportform";
    }

    @GetMapping("/report/listing")
    public String showListingreport(@RequestParam(name = "type", required = false) String type, Model model){
        if ("itemList".equals(type)) {
            List<Item> items = itemService.getAllItems();
            model.addAttribute("items", items);

        }else if("supplierList".equals(type)){
            List<Supplier> suppliers = supplierService.getALLsuppliers();
            model.addAttribute("suppliers", suppliers);
        }
        return "listingreport";
    }

    @GetMapping("/report/transactional")
    public String showTransactionreport(@RequestParam(name = "transactionType", required = false) String transactionType,
                                        @RequestParam(name = "startDate", required = false) String startDate,
                                        @RequestParam(name = "endDate", required=false)String endDate,
                                        Model model   ){
        
        if(transactionType != null && startDate != null && endDate != null){
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            if("receivedItems".equals(transactionType)){
                List<ReceivedItem> receivedItems = receivedItemService.getReceiveditemsBetweendates(start, end);
                model.addAttribute("receivedItems", receivedItems);
            }else if("issuedItems".equals(transactionType)){
                List<IsssueItem> issuedItems = issueItemService.getIsseudItemsBetwwendates(start, end);
                model.addAttribute("issuedItems", issuedItems);
            }
        }                                    
        return "transactionalreport";
    }

   

}
