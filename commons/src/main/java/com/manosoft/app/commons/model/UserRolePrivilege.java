package com.manosoft.app.commons.model;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblUserRolePrivilege")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = "userRoles")
@EqualsAndHashCode(exclude = "userRoles")
public class UserRolePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRolePrivilegeId;

    @Column(nullable = false,unique = true,name = "userRolePrivilegeName")
    private String userRolePrivilegeName;
    
    @ManyToMany(mappedBy = "userRolePrivileges")
    private Collection<UserRole> userRoles;
}
