package com.talentpath.budgetmanager.models;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;

public class Transaction {
    Integer transactionId;
    Integer userId;
    BigInteger amount;
    boolean charge;//true = transaction is a charge (debit), false = transaction is income (credit)
    String description;
    Calendar date;

    public Transaction() {}
    public Transaction(Transaction toCopy) {
        this.transactionId = toCopy.transactionId;
        this.userId = toCopy.userId;
        this.amount = toCopy.amount;
        this.charge = toCopy.charge;
        this.description = toCopy.description;
        this.date = toCopy.date;
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
}
