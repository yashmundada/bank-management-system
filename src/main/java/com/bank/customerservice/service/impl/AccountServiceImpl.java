package com.bank.customerservice.service.impl;

import com.bank.customerservice.dto.*;
import com.bank.customerservice.entity.Account;
import com.bank.customerservice.enums.AccountStatus;
import com.bank.customerservice.enums.TransactionStatus;
import com.bank.customerservice.enums.TransactionType;
import com.bank.customerservice.exception.AccountNotFoundException;
import com.bank.customerservice.exception.CustomerNotFoundException;
import com.bank.customerservice.exception.InsufficientBalanceException;
import com.bank.customerservice.repository.AccountRepository;
import com.bank.customerservice.repository.TransactionRepository;
import com.bank.customerservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.bank.customerservice.entity.Transaction;
import org.springframework.transaction.annotation.Transactional;
import com.bank.customerservice.repository.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger log =
            LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

@Override
public AccountResponse createAccount(AccountRequest request) {

    log.info("Creating account for customer ID: {}", request.getCustomerId());

    if (!customerRepository.existsByCustomerId(request.getCustomerId())) {
        log.warn("Account creation failed. Customer not found: {}", request.getCustomerId());
        throw new CustomerNotFoundException("Customer not found");
    }

    Account account = Account.builder()
            .accountNumber(generateAccountNumber())
            .customerId(request.getCustomerId())
            .accountType(request.getAccountType())
            .balance(BigDecimal.ZERO)
            .branch(request.getBranch())
            .status(AccountStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

    Account savedAccount = accountRepository.save(account);

    log.info("Account created successfully. Account Number: {}, Customer ID: {}",
            savedAccount.getAccountNumber(),
            savedAccount.getCustomerId());

    return mapToResponse(savedAccount);
}

    @Override
    public List<AccountResponse> getAllAccounts() {

        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AccountResponse getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        return mapToResponse(account);
    }

    @Override
    public AccountResponse updateAccount(Long id, AccountRequest request) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        account.setCustomerId(request.getCustomerId());
        account.setAccountType(request.getAccountType());
        account.setBranch(request.getBranch());

        Account updatedAccount = accountRepository.save(account);

        return mapToResponse(updatedAccount);
    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found"));

        accountRepository.delete(account);
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .customerId(account.getCustomerId())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .branch(account.getBranch())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .build();
    }

    private String generateAccountNumber() {

        Random random = new Random();

        return "ACC" + (100000000 + random.nextInt(900000000));
    }



@Override
public AccountResponse depositMoney(DepositRequest request) {

    log.info("Depositing amount {} into account {}",
            request.getAmount(),
            request.getAccountNumber());

    Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> {
                log.warn("Deposit failed. Account not found: {}",
                        request.getAccountNumber());
                return new AccountNotFoundException("Account not found");
            });

    // Update balance
    account.setBalance(account.getBalance().add(request.getAmount()));

    // Save updated account
    Account updatedAccount = accountRepository.save(account);

    // Save transaction history
    Transaction transaction = Transaction.builder()
            .transactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
            .fromAccount(null)
            .toAccount(account.getAccountNumber())
            .transactionType(TransactionType.DEPOSIT)
            .amount(request.getAmount())
            .status(TransactionStatus.SUCCESS)
            .remarks("Amount deposited successfully")
            .transactionDate(LocalDateTime.now())
            .build();

    transactionRepository.save(transaction);

    log.info("Deposit successful. Transaction ID: {}, Account: {}, Amount: {}, New Balance: {}",
            transaction.getTransactionId(),
            updatedAccount.getAccountNumber(),
            request.getAmount(),
            updatedAccount.getBalance());

    return mapToResponse(updatedAccount);
}

    @Override
    public AccountResponse withdrawMoney(WithdrawRequest request) {

        log.info("Withdrawing amount {} from account {}",
                request.getAmount(),
                request.getAccountNumber());

        // Find account
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> {
                    log.warn("Withdrawal failed. Account not found: {}",
                            request.getAccountNumber());
                    return new AccountNotFoundException("Account not found");
                });

        // Check balance
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            log.warn("Withdrawal failed. Insufficient balance in account {}. Available Balance: {}, Requested Amount: {}",
                    account.getAccountNumber(),
                    account.getBalance(),
                    request.getAmount());

            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Update balance
        account.setBalance(account.getBalance().subtract(request.getAmount()));

        // Save updated account
        Account updatedAccount = accountRepository.save(account);

        // Save transaction history
        Transaction transaction = Transaction.builder()
                .transactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .fromAccount(account.getAccountNumber())
                .toAccount(null)
                .transactionType(TransactionType.WITHDRAW)
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .remarks("Amount withdrawn successfully")
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        log.info("Withdrawal successful. Transaction ID: {}, Account: {}, Amount: {}, Remaining Balance: {}",
                transaction.getTransactionId(),
                updatedAccount.getAccountNumber(),
                request.getAmount(),
                updatedAccount.getBalance());

        return mapToResponse(updatedAccount);
    }

    @Override
    @Transactional
    public AccountResponse transferMoney(TransferRequest request) {

        log.info("Initiating transfer of amount {} from account {} to account {}",
                request.getAmount(),
                request.getFromAccount(),
                request.getToAccount());

        Account sender = accountRepository.findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> {
                    log.warn("Transfer failed. Sender account not found: {}",
                            request.getFromAccount());
                    return new AccountNotFoundException("Sender account not found");
                });

        Account receiver = accountRepository.findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> {
                    log.warn("Transfer failed. Receiver account not found: {}",
                            request.getToAccount());
                    return new AccountNotFoundException("Receiver account not found");
                });

        if (sender.getAccountNumber().equals(receiver.getAccountNumber())) {
            log.warn("Transfer failed. Sender and receiver account cannot be the same: {}",
                    sender.getAccountNumber());
            throw new IllegalArgumentException("Sender and receiver account cannot be same");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            log.warn("Transfer failed. Insufficient balance in sender account {}. Available Balance: {}, Requested Amount: {}",
                    sender.getAccountNumber(),
                    sender.getBalance(),
                    request.getAmount());

            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Debit sender
        sender.setBalance(sender.getBalance().subtract(request.getAmount()));

        // Credit receiver
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction transaction = Transaction.builder()
                .transactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .fromAccount(sender.getAccountNumber())
                .toAccount(receiver.getAccountNumber())
                .transactionType(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .remarks("Amount transferred successfully")
                .transactionDate(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);

        log.info("Transfer successful. Transaction ID: {}, From Account: {}, To Account: {}, Amount: {}, Sender Balance: {}, Receiver Balance: {}",
                transaction.getTransactionId(),
                sender.getAccountNumber(),
                receiver.getAccountNumber(),
                request.getAmount(),
                sender.getBalance(),
                receiver.getBalance());

        return mapToResponse(sender);
    }
}