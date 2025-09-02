package com.manosoft.app.user_service.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manosoft.app.commons.dto.ChangePasswordRequestDTO;
import com.manosoft.app.commons.dto.UserRequestDTO;
import com.manosoft.app.commons.dto.UserSignInRequestDTO;
import com.manosoft.app.commons.model.User;
import com.manosoft.app.commons.model.UserRole;
import com.manosoft.app.commons.response.UserResponseRecord;
import com.manosoft.app.user_service.exception.UserNotFoundException;
import com.manosoft.app.user_service.repository.UserRepository;
import com.manosoft.app.user_service.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailService appUserDetailService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String createUser(@NonNull UserRequestDTO requestDTO) throws MessagingException {
        LocalDate currentDT = LocalDate.now();
        User user = UserMapper.INSTANCE.fromUserRequestDTO(requestDTO);
        user.setUserCreationDT(currentDT);
        user.getPassword().setPasswordCreationDT(currentDT);
        UserRole userRole = userRoleRepository.findByUserRoleName("ROLE_USER");
        user.setUserRoles(Set.of(userRole));
        user.setUserAccountEnabled(true);
        user.setUserAccountNonExpired(true);
        user.setUserAccountNonLocked(true);
        user.setUserCredentialsNonExpired(true);
        user.getPassword().setEncryptedPassword(passwordEncoder.encode(user.getPassword().getEncryptedPassword()));
        userRepository.save(user);
        //mailService.sendEmailFromTemplate("manosoft.creation@gmail.com", user.getUserEmail());
        return "Thank You for Registration.";
    }

    @Transactional
    public UserResponseRecord findUserByItsId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);
        if (Objects.nonNull(user)) {
            return UserMapper.INSTANCE.fromUserModelToResponseDTO(user);
        }
        return null;
    }

    @Transactional
    public String authenticate(UserSignInRequestDTO userSignInRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignInRequestDTO.userEmailOrMobile(), userSignInRequestDTO.userPassword()));
        UserDetails userDetails = appUserDetailService.loadUserByUsername(userSignInRequestDTO.userEmailOrMobile());
        return jwtService.generateToken(userDetails);
    }

    @Transactional
    public Set<UserResponseRecord> findAllUsers() {
        return UserMapper.INSTANCE.fromUserModelsToUserResponseDTOs(new HashSet<>(userRepository.findAll()));
    }

    @Transactional
    public boolean forgetPassword(String userEmailOrMobile) {
        return Objects.nonNull(appUserDetailService.loadUserByUsername(userEmailOrMobile));
    }

    public UserResponseRecord getUserByEmailOrMobile(String userEmailOrMobile) {
        return null;
    }

    @Transactional
    public String changePasswordRequest(ChangePasswordRequestDTO changePasswordRequestDTO) {
        String currentPassword, newPassword, confirmPassword;
        if (Objects.nonNull(changePasswordRequestDTO)) {
            Optional<User> byUserEmail = userRepository.findByUserEmail(changePasswordRequestDTO.userEmail().trim());
            User user = byUserEmail.orElseThrow(() -> new UserNotFoundException("Unable to find user."));
            currentPassword = changePasswordRequestDTO.oldPassword().trim();
            newPassword = changePasswordRequestDTO.newPassword().trim();
            confirmPassword = changePasswordRequestDTO.confirmPassword().trim();
            if (newPassword.equals(confirmPassword) && passwordEncoder.matches(currentPassword,user.getPassword().getEncryptedPassword())) {
                user.getPassword().setEncryptedPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return "Password changed successfully";
            }
            return "Old Password Not Matched";
        }
        return null;
    }

}
