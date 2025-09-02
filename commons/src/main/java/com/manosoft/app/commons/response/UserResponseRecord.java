package com.manosoft.app.commons.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public record UserResponseRecord(
    Long userId,
        String userFName,
        String userLName,
        String userMobile,
        String userEmail,
        LocalDate userDOB,
        LocalDateTime userCreationDT,
        Collection<UserRoleResponseRecord> userRoles
) {

}
