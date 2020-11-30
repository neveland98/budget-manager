package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findAllByAssociatedUser_UserId(Integer userId);
}
