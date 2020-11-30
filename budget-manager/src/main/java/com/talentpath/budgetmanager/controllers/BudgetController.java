package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BudgetController {

    @Autowired
    BudgetService service;

    @GetMapping("/transactions/{id}")
    public List<Transaction> getAllTransactions(@PathVariable Integer id) {
        return service.getAllTransactions(id);
    }

    @PostMapping("/transactions")
    public Integer addTransaction(@RequestBody Transaction toAdd) {
        return service.addTransaction(toAdd);
    }

    @PutMapping("/transactions")
    public Integer updateTransaction(@RequestBody Transaction updated) { return service.updateTransaction(updated);}

    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Integer id) throws BudgetDaoException { service.deleteById(id); }

}
