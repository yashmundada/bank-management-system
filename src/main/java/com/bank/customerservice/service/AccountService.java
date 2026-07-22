package com.bank.customerservice.service;

import com.bank.customerservice.dto.*;

import java.util.List;

public interface AccountService {


    AccountResponse createAccount(AccountRequest request);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountById(Long id);

    AccountResponse updateAccount(Long id, AccountRequest request);

    void deleteAccount(Long id);

    AccountResponse depositMoney(DepositRequest request);

    AccountResponse withdrawMoney(WithdrawRequest request);

    AccountResponse transferMoney(TransferRequest request);
}