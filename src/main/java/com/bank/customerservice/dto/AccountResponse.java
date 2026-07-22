
package com.bank.customerservice.dto;

import com.bank.customerservice.enums.AccountStatus;
import com.bank.customerservice.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponse {

    private String accountNumber;
    private String customerId;
    private AccountType accountType;
    private BigDecimal balance;
    private String branch;
    private AccountStatus status;
    private LocalDateTime createdAt;
}