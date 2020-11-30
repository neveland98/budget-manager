package com.talentpath.budgetmanager.services;

import com.talentpath.budgetmanager.daos.BudgetDao;
import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    BudgetDao dao;

    @Autowired
    public BudgetService(BudgetDao dao) {this.dao = dao;}

    public List<Transaction> makeTransaction(Transaction userTransaction) {
        dao.addTransaction(userTransaction);
        return dao.getAllTransactions(userTransaction.getUserId());
    }
    public List<Transaction> getAllTransactions(Integer id) {
        return dao.getAllTransactions(id);
    }
    public Transaction getTransactionById(Integer transactionId) {
        return dao.getTransactionById(transactionId);
    }

    public Integer addTransaction(Transaction toAdd) {
        return dao.addTransaction(toAdd);
    }

    public void deleteById(Integer id) throws BudgetDaoException {
        dao.deleteTransactionById(id);
    }

    public Integer updateTransaction(Transaction updated) {
        return dao.editTransaction(updated);
    }
}
