package com.talentpath.budgetmanager.services;

import com.talentpath.budgetmanager.daos.BudgetDao;
import com.talentpath.budgetmanager.daos.CategoryRepository;
import com.talentpath.budgetmanager.daos.RoleRepository;
import com.talentpath.budgetmanager.daos.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {

    @Autowired
    BudgetDao dao;
    @Autowired
    UserRepository repo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    CategoryRepository categoryRepo;

    @BeforeEach
    void setUp() {
    }

}