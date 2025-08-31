package com.manosoft.app.commons.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tblCustomer")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = {"customerAddress"})
@EqualsAndHashCode(exclude = {"customerAddress"})
public class Customer {

    private String custId;

    private User user;

    private Set<CustomerAddress> customerAddress;

    @Column(name = "custCreationDT", nullable = false)
    private LocalDateTime custCreationDT;

}
