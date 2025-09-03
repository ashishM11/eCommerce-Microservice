package com.manosoft.app.commons.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tblPassword")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;

    @Column(name = "password")
    private String encryptedPassword;

    @Column(name = "passwordCreationDT")
    private LocalDateTime passwordCreationDT;

    @Column(name = "passwordCreationDT")
    private LocalDateTime passwordLastUpdateDT;

}
