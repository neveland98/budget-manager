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
            assertEquals(toAdd.getAmount(),toTest.getAmount());
            assertEquals(toAdd.getCategory(),toTest.getCategory());
//            assertEquals(toAdd.getDate(),toTest.getDate()); we actually don't need to do this because we don't care about the actual millisecond time value (if we did, we would store that value as a long in the database, rather than a date value)
            //instead, we want to compare the month, day, year value
            assertEquals(toAdd.getDate().get(Calendar.YEAR),toTest.getDate().get(Calendar.YEAR));
            assertEquals(toAdd.getDate().get(Calendar.MONTH),toTest.getDate().get(Calendar.MONTH));
            assertEquals(toAdd.getDate().get(Calendar.DATE),toTest.getDate().get(Calendar.DATE));
            assertEquals(toAdd.getDescription(),toTest.getDescription());
            assertEquals(toAdd.getUserId(),toTest.getUserId());
            assertEquals(toAdd.isCharge(),toTest.isCharge());
        }
        catch(Exception e) {
            fail("exception caught on golden path: " + e.getMessage());
        }


    }

    @Test
    void getAllTransactions() {
        //aaa
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