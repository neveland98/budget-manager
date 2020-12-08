package com.talentpath.budgetmanager.models;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

/**
 * This class represents a Transaction.
 */
public class Transaction {
    Integer transactionId;
    Integer userId;
    BigInteger amount;
    boolean charge;//true = transaction is a charge (debit), false = transaction is income (credit)
    String description;
    Calendar date;
    Category category;

    /**
     * Create a new Transaction with null parameters.
     */
    public Transaction() {}

    /**
     * Create a new Transaction with pre-defined parameters. This should only be used for testing purposes.
     * Note that this constructor does not check for invalid or null parameters. Please don't use this in any actual code.
     * @param userId userId for the associeated User.
     * @param amount Amount for the transaction (cents, as a BigInteger).
     * @param charge True if transaction is a debit, false if transaction is a credit.
     * @param description String transaction description.
     * @param date Date the transaction took place (As a java.util.Calendar)
     * @param category Category the transaction is associated with.
     */
    public Transaction(Integer userId, BigInteger amount, boolean charge, String description, Calendar date, Category category) {
        this.transactionId = 0;
        this.userId = userId;
        this.amount = amount;
        this.charge = charge;
        this.description = description;
        this.date = date;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Transaction(Transaction toCopy) {
        this.transactionId = toCopy.transactionId;
        this.userId = toCopy.userId;
        this.amount = toCopy.amount;
        this.charge = toCopy.charge;
        this.description = toCopy.description;
        this.date = toCopy.date;
        this.category = toCopy.category;
    }

    public Integer getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        else if(obj == null || getClass() != obj.getClass()) return false;

        Transaction that = (Transaction) obj;

        //note: we don't compare values that are determined by the database, such as transaction id and category id, because we may put in a default value in a unit test or something and we would erroneously get false for .equals in that case
        if(!Objects.equals(userId,that.userId)) return false;
        else if(!Objects.equals(amount,that.amount)) return false;
        else if(!Objects.equals(charge,that.charge)) return false;
        else if(!Objects.equals(description,that.description)) return false;

        else if(!Objects.equals(this.date.get(Calendar.YEAR),that.date.get(Calendar.YEAR))) return false;
        else if(!Objects.equals(this.date.get(Calendar.MONTH),that.date.get(Calendar.MONTH))) return false;
        else if(!Objects.equals(this.date.get(Calendar.DATE),that.date.get(Calendar.DATE))) return false;

        else if(!Objects.equals(this.category.getCategoryName(),that.category.getCategoryName())) return false;
        else if(!Objects.equals(this.category.getUser_id(),that.category.getUser_id())) return false;

        else return true;
    }
}
