package com.bank.customerservice.entity;

import com.bank.customerservice.enums.AccountStatus;
import com.bank.customerservice.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private String customerId;


@Enumerated(EnumType.STRING)
@Column(name = "account_type", nullable = false)
private AccountType accountType;
    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String branch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}