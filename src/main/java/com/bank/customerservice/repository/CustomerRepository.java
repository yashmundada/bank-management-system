
package com.bank.customerservice.repository;

import com.bank.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    boolean existsByCustomerId(String customerId);
}