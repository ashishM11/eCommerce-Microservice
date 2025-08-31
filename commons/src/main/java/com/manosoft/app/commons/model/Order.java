package com.manosoft.app.commons.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tblOrder")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString(exclude = "orderItems")
@EqualsAndHashCode(exclude = "orderItems")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_sequence", allocationSize = 1)
    private Long orderNumber;   // PK

    @Column(unique = true, updatable = false)
    private String orderId;     // Business ID like ORD000000001

    @PrePersist
    private void generateOrderId() {
        if (this.orderId == null && this.orderNumber != null) {
            this.orderId = String.format("ORD%09d", this.orderNumber);
        }
    }

    @Column(nullable = false)
    private Long customerId;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @Column(nullable = false)
    private double totalOrderAmount;

    @Column(columnDefinition = "varchar(10) default 'PENDING'")
    private String orderStatus;

    @Column(nullable = false)
    private LocalDateTime orderCreationDT;

    private LocalDateTime orderLastUpdateDT;
}