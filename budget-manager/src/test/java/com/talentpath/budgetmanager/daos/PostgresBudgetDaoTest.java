package com.talentpath.budgetmanager.daos;

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

            Transaction get1 = dao.getTransactionById(1),
                    get2 = dao.getTransactionById(2),
                    get3 = dao.getTransactionById(3);

            assertEquals(transaction1,get1);
            assertEquals(transaction2,get2);
            assertEquals(transaction3,get3);

        }
        catch(Exception e) {
            fail("Exception caught on golden path test: " + e.getMessage());
        }
    }

    @Test
    void getRunningTotal() {

    }


    @Test
    void editTransaction() {
    }

    @Test
    void deleteTransactionById() {
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