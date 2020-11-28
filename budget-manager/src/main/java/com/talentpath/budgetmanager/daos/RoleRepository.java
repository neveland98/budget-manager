package com.talentpath.budgetmanager.daos;

import com.talentpath.budgetmanager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(Role.RoleName name);
}
