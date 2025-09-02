package com.manosoft.app.user_service.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manosoft.app.commons.model.User;
import com.manosoft.app.user_service.exception.UserNotFoundException;
import com.manosoft.app.user_service.model.ApplicationUser;
import com.manosoft.app.user_service.repository.UserRepository;

import java.util.Optional;

@Service
public class AppUserDetailService implements UserDetailsService {

    public final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String emailOrMobile) throws UsernameNotFoundException {
        Optional<User> userOptional = emailOrMobile.contains("@") ? userRepository.findByUserEmail(emailOrMobile) : userRepository.findByUserMobile(emailOrMobile);
        return userOptional
                .map(ApplicationUser::new)
                .orElseThrow(() -> new UserNotFoundException("Email Or Mobile number provided not found"));
    }
}