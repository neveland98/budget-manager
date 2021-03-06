package com.talentpath.budgetmanager.services;

import com.talentpath.budgetmanager.daos.BudgetDao;
import com.talentpath.budgetmanager.daos.CategoryRepository;
import com.talentpath.budgetmanager.exceptions.*;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

@Service
public class BudgetService {
    BudgetDao dao;
    @Autowired
    CategoryRepository repo;

    @Autowired
    public BudgetService(BudgetDao dao) {this.dao = dao;}

    public List<Transaction> getAllTransactions(Integer id) throws InvalidUserIdException, NullArgumentException {
        return dao.getAllTransactions(id);
    }
    public Transaction getTransactionById(Integer transactionId) throws BudgetDaoException, NullArgumentException {
        return dao.getTransactionById(transactionId);
    }

    public Integer addTransaction(Transaction toAdd) throws NullArgumentException, NullParameterException, InvalidArgumentException {
        return dao.addTransaction(toAdd);
    }

    public void deleteById(Integer id) throws BudgetDaoException, NullArgumentException {
        dao.deleteTransactionById(id);
    }

    public Integer updateTransaction(Transaction updated) throws NullArgumentException, NullParameterException, InvalidArgumentException {
        return dao.editTransaction(updated);
    }

    public List<Category> getUserCategories(Integer id) {
        return repo.findAllByAssociatedUser_UserId(id);
    }

    public Category addCategory(Category toAdd) throws BudgetDaoException {
        return dao.addCategory(toAdd);
    }

    public Category getCategoryById(Integer id) throws BudgetDaoException {
        return dao.getCategoryById(id);
    }

    public Integer updateCategory(Category updated) throws BudgetDaoException, NullArgumentException, InvalidArgumentException {
        return dao.editCategory(updated);
    }

    public void deleteCategoryById(Integer id) throws NullArgumentException {
        dao.deleteCategoryById(id);
    }

    public BigInteger getRunningTotal(Integer id) throws NullArgumentException {
        return dao.getRunningTotal(id);
    }

    public BigInteger getMonthlyTotal(Integer id) {
        Calendar now = Calendar.getInstance();
        java.sql.Date date = new java.sql.Date(now.getTimeInMillis());
        return dao.getMonthlyTotal(id,date.toString());
    }
}
