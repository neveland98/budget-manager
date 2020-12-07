package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.BudgetDaoException;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface BudgetDao {
    List<Transaction> getAllTransactions(Integer id);

    /**
     * Adds a new Transaction to the database.
     * @param userTransaction transaction to add.
     * @return Integer transaction id of the newly added Transaction.
     */
    Integer addTransaction(Transaction userTransaction);

    Transaction getTransactionById(Integer transactionId) throws BudgetDaoException;

    void deleteTransactionById(Integer id) throws BudgetDaoException;

    void reset();
    Integer editTransaction(Transaction updated);

    Category addCategory(Category userCategory) throws BudgetDaoException;
    Category getCategoryById(Integer categoryId) throws BudgetDaoException;
    Integer editCategory(Category updated) throws BudgetDaoException;
    void deleteCategoryById(Integer categoryId);

    BigInteger getRunningTotal(Integer id);

    BigInteger getMonthlyTotal(Integer id, String toString);
}
