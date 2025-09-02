package com.manosoft.app.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manosoft.app.commons.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    UserRole findByUserRoleName(String roleAdmin);
}
