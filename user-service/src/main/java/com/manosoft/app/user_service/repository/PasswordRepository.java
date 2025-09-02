package com.manosoft.app.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manosoft.app.commons.model.Password;

@Repository
public interface PasswordRepository extends JpaRepository<Password,Long> {
}
