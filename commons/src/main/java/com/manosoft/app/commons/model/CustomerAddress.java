package com.manosoft.app.commons.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblCustAddress")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = {"customer"})
@EqualsAndHashCode(exclude = {"customer"})
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custid", nullable = false) // FK to User
    private Customer customer;

    @Column(nullable = false, length = 150)
    private String street;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 100)
    private String state;

    @Column(nullable = false, length = 100)
    private String country;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, columnDefinition = "varchar(20) default 'HOME'")
    private String addressType; 

}
