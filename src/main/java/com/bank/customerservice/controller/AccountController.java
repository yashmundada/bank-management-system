package com.bank.customerservice.controller;

import com.bank.customerservice.dto.*;
import com.bank.customerservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bank.customerservice.dto.TransferRequest;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(
            @Valid @RequestBody AccountRequest request) {

        AccountResponse account = accountService.createAccount(request);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.CREATED.value(),
                        "Account created successfully",
                        account);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {

        List<AccountResponse> accounts = accountService.getAllAccounts();

        ApiResponse<List<AccountResponse>> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Accounts fetched successfully",
                        accounts);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(
            @PathVariable Long id) {

        AccountResponse account = accountService.getAccountById(id);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Account fetched successfully",
                        account);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<AccountResponse>> depositMoney(
            @Valid @RequestBody DepositRequest request) {

        AccountResponse account = accountService.depositMoney(request);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Amount deposited successfully",
                        account);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<AccountResponse>> withdrawMoney(
            @Valid @RequestBody WithdrawRequest request) {

        AccountResponse account = accountService.withdrawMoney(request);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Amount withdrawn successfully",
                        account);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<AccountResponse>> transferMoney(
            @Valid @RequestBody TransferRequest request) {

        AccountResponse responseData = accountService.transferMoney(request);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Amount transferred successfully",
                        responseData);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request) {

        AccountResponse account = accountService.updateAccount(id, request);

        ApiResponse<AccountResponse> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Account updated successfully",
                        account);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(
            @PathVariable Long id) {

        accountService.deleteAccount(id);

        ApiResponse<String> response =
                new ApiResponse<>(
                        HttpStatus.OK.value(),
                        "Account deleted successfully",
                        null);

        return ResponseEntity.ok(response);
    }
}