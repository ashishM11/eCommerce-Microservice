package com.manosoft.app.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manosoft.app.commons.model.UserRolePrivilege;

@Repository
public interface UserRolePrivilegeRepository extends JpaRepository<UserRolePrivilege, Long> {
    UserRolePrivilege findByUserRolePrivilegeName(String privilegeName);
}