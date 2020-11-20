package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Transaction;

import java.util.List;

public interface BudgetDao {
    List<Transaction> getAllTransactions();

    Integer addTransaction(Transaction userTransaction);

    Transaction getTransactionById(Integer transactionId);
}
