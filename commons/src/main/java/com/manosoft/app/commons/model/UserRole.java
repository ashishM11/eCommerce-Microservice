package com.manosoft.app.commons.model;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblUserRole")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = {"userRoleId","users"})
@EqualsAndHashCode(exclude = {"userRoleId","users"})
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    @Column(unique = true, name = "userRoleName", nullable = false)
    private String userRoleName;

    @ManyToMany(mappedBy = "userRoles")
    private Collection<User> users;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tblUserRoleWithPrivileges", joinColumns = @JoinColumn(name = "userRoleId", referencedColumnName = "userRoleId"), inverseJoinColumns = @JoinColumn(name = "userRolePrivilegeId", referencedColumnName = "userRolePrivilegeId"))
    private Collection<UserRolePrivilege> userRolePrivileges;

}
