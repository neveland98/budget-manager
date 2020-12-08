package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.exceptions.InvalidUserIdException;
import com.talentpath.budgetmanager.exceptions.NullArgumentException;
import com.talentpath.budgetmanager.exceptions.NullParameterException;
import com.talentpath.budgetmanager.models.Category;
import com.talentpath.budgetmanager.models.Transaction;
import com.talentpath.budgetmanager.models.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("daotesting")
class PostgresBudgetDaoTest {

    @Autowired
    BudgetDao dao;
    @Autowired
    UserRepository repo;
    @Autowired
    RoleRepository roleRepo;

    @BeforeEach
    @Transactional
    void setUp() {
        dao.reset();
        repo.saveAndFlush(new User("username","email@gmail.com","password"));
    }

    @Test
    void addTransaction() {
        try {
            Category testCategory = new Category(1,"general",1);
            dao.addCategory(testCategory);
            Calendar calendar = Calendar.getInstance();
            Transaction toAdd = new Transaction(1, BigInteger.valueOf(500L),true,"test transaction", calendar ,testCategory);
            Integer id = dao.addTransaction(toAdd);
            Transaction toTest = dao.getTransactionById(id);
            assertEquals(toAdd,toTest);
        }
        catch(Exception e) {
            fail("exception caught on golden path: " + e.getMessage());
        }


    }

    @Test
    void addTransactionNullTransaction() {
        try {
            dao.addTransaction(null);
            fail("No exception caught.");
        }
        catch(NullArgumentException e) {
            //pass
        }
        catch(Exception e) {
            fail("Wrong exception caught: " + e.getMessage());
        }
    }

    @Test
    void addTransactionNullParameter() {
        try {
            Transaction transaction = new Transaction(1,null,true,"haha",null,null);
            dao.addTransaction(transaction);
            fail("No exception caught.");
        }
        catch(NullParameterException e) {
            //pass
        }
        catch(Exception e) {
            fail("Wrong exception caught: " + e.getMessage());
        }
    }

    @Test
    void getAllTransactions() {
        try {
            Category category1 = new Category(1,"general",1);
            Category category2 = new Category(2, "food", 1);
            Category category3 = new Category(3, "misc", 1);
            Calendar calendar = Calendar.getInstance();

            Transaction transaction1 = new Transaction(1,BigInteger.valueOf(1000L),true,"generic",calendar,category1),
                    transaction2 = new Transaction(1,BigInteger.valueOf(4000L),true,"groceries",calendar,category2),
                    transaction3 = new Transaction(1,BigInteger.valueOf(200L),true,"gum",calendar,category3);

            dao.addCategory(category1);
            dao.addCategory(category2);
            dao.addCategory(category3);

            dao.addTransaction(transaction1);
            dao.addTransaction(transaction2);
            dao.addTransaction(transaction3);

            List<Transaction> allTransactions = dao.getAllTransactions(1);

            assertEquals(transaction1,allTransactions.get(0));
            assertEquals(transaction2,allTransactions.get(1));
            assertEquals(transaction3,allTransactions.get(2));

        }
        catch(Exception e) {
            fail("Exception caught on golden path test: " + e.getMessage());
        }
    }

    //note: userId checking is done in service layer

    @Test
    void getAllTransactionsNullUserId() {
        try {
            dao.getAllTransactions(null);
            fail("No exception caught.");
        }
        catch(NullArgumentException e) {
            //pass
        }
        catch(Exception e) {
            fail("Wrong exception caught: " + e.getMessage());
        }
    }

    @Test
    void getRunningTotal() {
        try {
            Category general = new Category(1, "general", 1);
            dao.addCategory(general);
            Calendar calendar = Calendar.getInstance();

            Transaction toAdd = new Transaction(1, BigInteger.valueOf(1000L), true, "test", calendar, general);
            Transaction toAdd2 = new Transaction(1, BigInteger.valueOf(20000L), false, "income", calendar, general);
            Transaction toAdd3 = new Transaction(1, BigInteger.valueOf(3535L), true, "weird", calendar, general);

            dao.addTransaction(toAdd);
            dao.addTransaction(toAdd2);
            dao.addTransaction(toAdd3);

            BigInteger total = dao.getRunningTotal(1);
            assertEquals(15465L,total.longValue());

        }
        catch(Exception e) {
            fail("Exception caught on golden path: " + e.getMessage());
        }
    }


    @Test
    void editTransaction() {
        try {
            Category general = new Category(1, "general", 1);
            dao.addCategory(general);
            Calendar calendar = Calendar.getInstance();

            Transaction toAdd = new Transaction(1, BigInteger.valueOf(1000L), true, "test", calendar, general);
            Transaction toAdd2 = new Transaction(1, BigInteger.valueOf(20000L), false, "income", calendar, general);
            Transaction toAdd3 = new Transaction(1, BigInteger.valueOf(3535L), true, "weird", calendar, general);

            dao.addTransaction(toAdd);
            dao.addTransaction(toAdd2);
            dao.addTransaction(toAdd3);

            toAdd3.setTransactionId(3);//no id was set before, edit transaction method needs the id to be set which it usually is because it will get the transaction before letting you edit it
            toAdd3.setDescription("edited");
            toAdd3.setAmount(BigInteger.valueOf(12000L));

            dao.editTransaction(toAdd3);

            Transaction toTest = dao.getTransactionById(3);

            assertEquals(toAdd3,toTest);
        }
        catch(Exception e) {
            fail("Exception caught on golden path: " + e.getMessage());
        }
    }

    @Test
    void deleteTransactionById() {
        try {
            Category general = new Category(1, "general", 1);
            dao.addCategory(general);
            Calendar calendar = Calendar.getInstance();

            Transaction toAdd = new Transaction(1, BigInteger.valueOf(1000L), true, "test", calendar, general);
            Transaction toAdd2 = new Transaction(1, BigInteger.valueOf(20000L), false, "income", calendar, general);
            Transaction toAdd3 = new Transaction(1, BigInteger.valueOf(3535L), true, "weird", calendar, general);

            dao.addTransaction(toAdd);
            dao.addTransaction(toAdd2);
            dao.addTransaction(toAdd3);

            List<Transaction> allTransactions = dao.getAllTransactions(1);
            List<Transaction> filteredList = allTransactions.stream().filter(transaction->!transaction.getTransactionId().equals(3)).collect(Collectors.toList());

            dao.deleteTransactionById(3);

            allTransactions = dao.getAllTransactions(1);
            assertEquals(filteredList,allTransactions);

        }
        catch(Exception e) {
            fail("Exception caught on golden path: " + e.getMessage());
        }
    }

    @Test
    void getTransactionById() {
    }

    @Test
    void addCategory() {
    }

    @Test
    void getCategoryById() {
    }

    @Test
    void editCategory() {
    }

    @Test
    void deleteCategoryById() {
    }

    @Test
    void getMonthlyTotal() {
    }
}