package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BudgetController {

    @Autowired
    BudgetService service;

    //GET
    @GetMapping("/transactions/{id}")
    public List<Transaction> getAllTransactions(@PathVariable Integer id) {
        return service.getAllTransactions(id);
    }

    @GetMapping("/transaction/{id}")
    public Transaction getTransactionById(@PathVariable Integer id) throws BudgetDaoException {return service.getTransactionById(id);}

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable Integer id) throws BudgetDaoException {return service.getCategoryById(id);}

    @CrossOrigin
    @GetMapping("/categories/{id}")
    public List<Category> getUserCategories(@PathVariable Integer id) {return service.getUserCategories(id);}

    @GetMapping("/total/{id}")
    public BigInteger getRunningTotal(@PathVariable Integer id) {return service.getRunningTotal(id);}

    @GetMapping("/monthly/{id}")
    public BigInteger getMonthlyTotal(@PathVariable Integer id) {return service.getMonthlyTotal(id);}
    //POST
    @PostMapping("/transactions")
    public Integer addTransaction(@RequestBody Transaction toAdd) {
        return service.addTransaction(toAdd);
    }
    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category toAdd) throws BudgetDaoException {return service.addCategory(toAdd);}
    //PUT
    @PutMapping("/transactions")
    public Integer updateTransaction(@RequestBody Transaction updated) { return service.updateTransaction(updated);}

    @PutMapping("/categories")
    public Integer updateCategory(@RequestBody Category updated) throws BudgetDaoException {return service.updateCategory(updated);}

    //DELETE
    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Integer id) throws BudgetDaoException { service.deleteById(id); }
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Integer id) {service.deleteCategoryById(id);}
}
