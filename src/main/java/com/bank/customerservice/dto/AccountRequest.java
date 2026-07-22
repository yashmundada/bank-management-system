package com.bank.customerservice.dto;

import com.bank.customerservice.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountRequest {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotBlank(message = "Branch is required")
    private String branch;
}