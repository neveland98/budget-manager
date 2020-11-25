package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins={"http://localhost:4200","http://0.0.0.0:4200"})
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

    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Integer id) throws BudgetDaoException { service.deleteById(id); }

}
