package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class BudgetController {

    @Autowired
    BudgetService service;

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping("/transactions")
    public Integer addTransaction(@RequestBody Transaction toAdd) {
        return service.addTransaction(toAdd);
    }


}
