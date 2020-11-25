package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Transaction;

import java.util.List;

public interface BudgetDao {
    List<Transaction> getAllTransactions(Integer id);

    Integer addTransaction(Transaction userTransaction);

    Transaction getTransactionById(Integer transactionId);

    void deleteTransactionById(Integer id) throws BudgetDaoException;
}
