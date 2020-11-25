package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.models.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"servicetesting"})
public class InMemBudgetDao implements BudgetDao{
    List<Transaction> allTransactions = new ArrayList<>();
    List<User> allUsers = new ArrayList<>();
    Integer nextTrId;
    Integer nextUserId;
    public InMemBudgetDao() {reset();}

    public void reset() {
        allTransactions.clear();
        allUsers.clear();

        User user1 = new User();
        user1.setUserId(1);
        user1.setUsername("testuser");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUserId(2);
        user2.setUsername("anotheruser");
        user2.setPassword("t3$tp4$$w0rd");

        Transaction t1 = new Transaction();
        t1.setAmount(BigInteger.valueOf(250));
        t1.setDescription("burger");
        t1.setCharge(true);
        t1.setTransactionId(1);
        t1.setUserId(1);

        Transaction t2 = new Transaction();
        t2.setAmount(BigInteger.valueOf(90000));
        t2.setDescription("rent");
        t2.setCharge(true);
        t2.setTransactionId(2);
        t2.setUserId(2);

        Transaction t3 = new Transaction();
        t3.setAmount(BigInteger.valueOf(1050));
        t3.setDescription("burger");
        t3.setUserId(2);
        t3.setTransactionId(3);
        t3.setCharge(true);

        Transaction t4 = new Transaction();
        t4.setAmount(BigInteger.valueOf(100000));
        t4.setDescription("paycheck");
        t4.setUserId(1);
        t4.setTransactionId(4);
        t4.setCharge(false);

    }

    @Override
    public List<Transaction> getAllTransactions(Integer userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer addTransaction(Transaction userTransaction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        throw new UnsupportedOperationException();
    }
}
