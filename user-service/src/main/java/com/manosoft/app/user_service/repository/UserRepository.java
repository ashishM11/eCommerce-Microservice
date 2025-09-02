package com.manosoft.app.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manosoft.app.commons.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserEmail(String emailOrMobile);

    Optional<User> findByUserMobile(String emailOrMobile);

}
