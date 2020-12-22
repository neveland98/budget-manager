package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.*;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;

import java.math.BigInteger;
import java.util.List;

public interface BudgetDao {
    /**
     * Gets all transactions from the database with matching userId.
     * @param userId Associated UserId for transactions to get.
     * @return <code>List</code> containing all transactions with matching userId.
     */
    List<Transaction> getAllTransactions(Integer userId) throws InvalidUserIdException, NullArgumentException;

    /**
     * Adds a new Transaction to the database.
     * @param userTransaction transaction to add.
     * @return Integer transaction id of the newly added Transaction.
     */
    Integer addTransaction(Transaction userTransaction) throws NullArgumentException, NullParameterException, InvalidArgumentException;

    /**
     * Gets a Transaction from the database with matching transactionId.
     * @param transactionId ID value of the desired Transaction.
     * @return A Transaction object.
     * @throws BudgetDaoException if there is not Transaction with matching transactionId in the database.
     */
    Transaction getTransactionById(Integer transactionId) throws BudgetDaoException, NullArgumentException;

    /**
     * Deletes a Transaction with matching transactionId from the database.
     * @param transactionId ID value of the Transaction to delete.
     * @throws BudgetDaoException if there is not Transaction with matching transactionId in the database.
     */
    void deleteTransactionById(Integer transactionId) throws BudgetDaoException, NullArgumentException;

    /**
     * Resets the database (for testing purposes only)
     */
    void reset();

    /**
     * Edits an existing Transaction in the database by updating its values to match the values of the given Transaction.
     * @param updated Updated Transaction with values used to overwrite the values for the existing Transaction.
     * @return TransactionId of the updated Transaction.
     */
    Integer editTransaction(Transaction updated) throws NullArgumentException, NullParameterException, InvalidCategoryException;

    /**
     * Adds given Category to the database.
     * @param userCategory Category to add.
     * @return Category that was added with updated categoryId.
     */
    Category addCategory(Category userCategory);

    /**
     * Gets Category with matching categoryId from the database.
     * @param categoryId CategoryId of desired Category.
     * @return Category with matching CategoryId.
     * @throws BudgetDaoException If there is no Category with matching categoryId in the database.
     */
    Category getCategoryById(Integer categoryId) throws BudgetDaoException;

    /**
     * Edits an existing Category in the database by updating its values to match the values of the given Category.
     * @param updated Updated Category with values used to overwrite the values for the existing Category.
     * @return CategoryId of the updated Category.
     */
    Integer editCategory(Category updated) throws NullArgumentException;

    /**
     * Deletes Category with matching categoryId from the database.
     * @param categoryId CategoryId of the Category to delete.
     */
    void deleteCategoryById(Integer categoryId);//todo: this should throw an exception if there is no category with matching id!

    /**
     * Gets running total of all transactions with matching userId from the database.
     * @param userId UserId associated with Transactions to sum.
     * @return java.math.BigInteger sum of all Transactions with associated userId.
     */
    BigInteger getRunningTotal(Integer userId) throws NullArgumentException;

    /**
     * Gets running total of all transactions for the month of passed date parameter from the database.
     * @param userId UserId associated with Transactions to sum.
     * @param dateString Date, formatted as yyyy-mm-dd, as a String.
     * @return java.math.BigInteger sum of all Transactions with associated userId for month of passed date.
     */
    BigInteger getMonthlyTotal(Integer userId, String dateString);
}
