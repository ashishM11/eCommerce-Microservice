package com.manosoft.app.commons.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.manosoft.app.commons.dto.UserRequestDTO;
import com.manosoft.app.commons.model.User;
import com.manosoft.app.commons.model.UserRole;
import com.manosoft.app.commons.model.UserRolePrivilege;
import com.manosoft.app.commons.response.UserResponseRecord;
import com.manosoft.app.commons.response.UserRolePrivilegeResponseRecord;
import com.manosoft.app.commons.response.UserRoleResponseRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "userAccountEnabled", ignore = true)
    @Mapping(target = "userAccountNonExpired", ignore = true)
    @Mapping(target = "userAccountNonLocked", ignore = true)
    @Mapping(target = "userCredentialsNonExpired", ignore = true)
    @Mapping(target = "userCreationDT", ignore = true)
    @Mapping(source = "rawPassword", target = "password.encryptedPassword")
    User fromUserRequestDTOToUserModel(UserRequestDTO requestDTO);

    @Mapping(source = "user.userRoles", target = "userRoles", qualifiedByName = "setUserRole")
    UserResponseRecord fromUserModelToResponseRecord(User user);

    @Named("setUserRole")
    @Mapping(source = "userRole.userRolePrivileges",target = "privileges")
    default UserRoleResponseRecord setUserRole(UserRole userRole) {
        Collection<UserRolePrivilegeResponseRecord> userRolePrivilegeResponseRecords = new ArrayList<>();
        for(UserRolePrivilege userRolePrivilege :userRole.getUserRolePrivileges()){
            userRolePrivilegeResponseRecords.add(setUserRolePrivilege(userRolePrivilege));
        }
        return new UserRoleResponseRecord(userRole.getUserRoleId(), userRole.getUserRoleName(),userRolePrivilegeResponseRecords);
    }

    default UserRolePrivilegeResponseRecord setUserRolePrivilege(UserRolePrivilege userRolePrivilege){
        return new UserRolePrivilegeResponseRecord(userRolePrivilege.getUserRolePrivilegeId(), userRolePrivilege.getUserRolePrivilegeName());
    }

    List<UserResponseRecord> fromUserModelsToUserResponseRecords(List<User> users);

}
