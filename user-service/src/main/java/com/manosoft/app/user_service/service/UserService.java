package com.manosoft.app.user_service.service;

import java.time.LocalDateTime;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import com.manosoft.app.commons.dto.ChangePasswordRequestDTO;
import com.manosoft.app.commons.dto.UserRequestDTO;
import com.manosoft.app.commons.dto.UserSignInRequestDTO;
import com.manosoft.app.commons.mapper.UserMapper;
import com.manosoft.app.commons.model.User;
import com.manosoft.app.commons.model.UserRole;
import com.manosoft.app.commons.response.UserResponseRecord;
import com.manosoft.app.user_service.exception.UserNotFoundException;
import com.manosoft.app.user_service.repository.UserRepository;
import com.manosoft.app.user_service.repository.UserRoleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailService appUserDetailService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String createUser(@NonNull UserRequestDTO requestDTO) {
        User user = UserMapper.INSTANCE.fromUserRequestDTOToUserModel(requestDTO);
        user.setUserCreationDT(LocalDateTime.now());
        user.getPassword().setPasswordCreationDT(LocalDateTime.now());
        UserRole userRole = userRoleRepository.findByUserRoleName("ROLE_USER");
        user.setUserRoles(Set.of(userRole));
        user.setUserAccountEnabled(true);
        user.setUserAccountNonExpired(true);
        user.setUserAccountNonLocked(true);
        user.setUserCredentialsNonExpired(true);
        user.getPassword().setEncryptedPassword(passwordEncoder.encode(user.getPassword().getEncryptedPassword()));
        try{
            userRepository.save(user);
        }catch(Exception objEx){
            log.error("Error Occured : ", objEx.getMessage());
        }
        return "Thank You for Registration.";
    }

    @Transactional(readOnly = true)
    public UserResponseRecord findUserByItsId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);
        if (Objects.nonNull(user)) {
            return UserMapper.INSTANCE.fromUserModelToResponseRecord(user);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public String authenticate(UserSignInRequestDTO userSignInRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignInRequestDTO.getUserEmailOrMobile(), userSignInRequestDTO.getUserPassword()));
        UserDetails userDetails = appUserDetailService.loadUserByUsername(userSignInRequestDTO.getUserEmailOrMobile());
        return jwtService.generateToken(userDetails);
    }

    @Transactional(readOnly = true)
    public Set<UserResponseRecord> findAllUsers() {
        return UserMapper.INSTANCE.fromUserModelsToUserResponseRecords(new HashSet<>(userRepository.findAll()));
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
        char[] currentPassword = changePasswordRequestDTO.getOldPassword().toCharArray();
        char[] newPassword = changePasswordRequestDTO.getNewPassword().toCharArray();
        char[] confirmPassword = changePasswordRequestDTO.getConfirmPassword().toCharArray();

        try {
            User user = userRepository.findByUserEmail(changePasswordRequestDTO.getUserEmail().trim())
                    .orElseThrow(() -> new UserNotFoundException("Unable to find user."));

            // compare new and confirm
            if (!Arrays.equals(newPassword, confirmPassword)) {
                return "New password and Confirm password do not match";
            }

            // check old password
            if (!passwordEncoder.matches(new String(currentPassword), user.getPassword().getEncryptedPassword())) {
                return "Old Password Not Matched";
            }

            // encode and save new password
            user.getPassword().setEncryptedPassword(passwordEncoder.encode(new String(newPassword)));
            userRepository.save(user);

            return "Password changed successfully";
        } finally {
            // wipe passwords from memory
            Arrays.fill(currentPassword, '\0');
            Arrays.fill(newPassword, '\0');
            Arrays.fill(confirmPassword, '\0');
        }
    }
}
