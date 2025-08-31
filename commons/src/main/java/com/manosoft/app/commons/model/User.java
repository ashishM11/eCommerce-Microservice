package com.manosoft.app.commons.model;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblUser")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = {"password","userRoles"})
@EqualsAndHashCode(exclude = {"password","userRoles"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "userFName", nullable = false, length = 25)
    private String userFName;

    @Column(name = "userLName", nullable = false, length = 25)
    private String userLName;

    @Column(name = "userMobile", nullable = false, length = 10, unique = true)
    private String userMobile;

    @Column(name = "userEmail", nullable = false, length = 45, unique = true)
    private String userEmail;

    @Column(name = "userGender", nullable = false)
    private String userGender;

    @Column(name = "userDOB")
    private LocalDate userDOB;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "passwordId", nullable = false, referencedColumnName = "passwordId")
    private Password password;

    @Column(name = "userAccountNonExpired")
    private boolean userAccountNonExpired;

    @Column(name = "userAccountNonLocked")
    private boolean userAccountNonLocked;

    @Column(name = "userCredentialsNonExpired")
    private boolean userCredentialsNonExpired;

    @Column(name = "userAccountEnabled")
    private boolean userAccountEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tblUsersWithRoles",
            joinColumns = @JoinColumn( name = "userId" , referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "userRoleId", referencedColumnName = "userRoleId")
    )
    private Collection<UserRole> userRoles;

}
