package com.bank.customerservice.repository;

import com.bank.customerservice.entity.Transaction;
import com.bank.customerservice.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);

    List<Transaction> findByFromAccount(String fromAccount);

    List<Transaction> findByToAccount(String toAccount);

    List<Transaction> findByTransactionType(TransactionType transactionType);
}