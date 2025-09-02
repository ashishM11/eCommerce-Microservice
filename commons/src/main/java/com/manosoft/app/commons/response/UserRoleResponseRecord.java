package com.manosoft.app.commons.response;

import java.util.Collection;

public record UserRoleResponseRecord(
    Long userRoleId,
        String userRoleName,
        Collection<UserRolePrivilegeResponseRecord> privileges
) {

}
