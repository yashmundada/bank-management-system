package com.bank.customerservice.service.impl;

import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.exception.CustomerNotFoundException;
import com.bank.customerservice.exception.DuplicateCustomerException;
import com.bank.customerservice.repository.CustomerRepository;
import com.bank.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log =
            LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;

@Override
public CustomerResponse createCustomer(CustomerRequest request) {

    log.info("Creating customer with email: {}", request.getEmail());

    if (customerRepository.existsByEmail(request.getEmail())) {
        log.warn("Customer creation failed. Email already exists: {}", request.getEmail());
        throw new DuplicateCustomerException("Email already exists");
    }

    if (customerRepository.existsByMobile(request.getMobile())) {
        log.warn("Customer creation failed. Mobile number already exists: {}", request.getMobile());
        throw new DuplicateCustomerException("Mobile number already exists");
    }

    Customer customer = Customer.builder()
            .customerId("CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .mobile(request.getMobile())
            .dateOfBirth(request.getDateOfBirth())
            .address(request.getAddress())
            .createdAt(LocalDateTime.now())
            .build();

    Customer savedCustomer = customerRepository.save(customer);

    log.info("Customer created successfully. Customer ID: {}, Email: {}",
            savedCustomer.getCustomerId(),
            savedCustomer.getEmail());

    return mapToResponse(savedCustomer);
}
@Override
public Page<CustomerResponse> getAllCustomers(int page,
                                              int size,
                                              String sortBy,
                                              String direction) {

    log.info("Fetching customers. Page: {}, Size: {}, Sort By: {}, Direction: {}",
            page, size, sortBy, direction);

    Sort sort = direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Customer> customerPage = customerRepository.findAll(pageable);

    log.info("Successfully fetched {} customers",
            customerPage.getNumberOfElements());

    return customerPage.map(this::mapToResponse);
}

@Override
public CustomerResponse getCustomerById(Long id) {

    log.info("Fetching customer with ID: {}", id);

    Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Customer not found with ID: {}", id);
                return new CustomerNotFoundException("Customer not found");
            });

    log.info("Customer fetched successfully. Customer ID: {}", customer.getCustomerId());

    return mapToResponse(customer);
}
    @Override
    public CustomerResponse getCustomerByCustomerId(String customerId) {

        log.info("Searching customer by Customer ID: {}", customerId);

        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> {
                    log.warn("Customer not found with Customer ID: {}", customerId);
                    return new CustomerNotFoundException("Customer not found");
                });

        log.info("Customer found. Customer ID: {}", customer.getCustomerId());

        return mapToResponse(customer);
    }
    @Override
    public CustomerResponse getCustomerByEmail(String email) {

        log.info("Searching customer by email: {}", email);

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Customer not found with email: {}", email);
                    return new CustomerNotFoundException("Customer not found");
                });

        log.info("Customer found. Email: {}", customer.getEmail());

        return mapToResponse(customer);
    }
    @Override
    public CustomerResponse getCustomerByMobile(String mobile) {

        log.info("Searching customer by mobile: {}", mobile);

        Customer customer = customerRepository.findByMobile(mobile)
                .orElseThrow(() -> {
                    log.warn("Customer not found with mobile: {}", mobile);
                    return new CustomerNotFoundException("Customer not found");
                });

        log.info("Customer found. Mobile: {}", customer.getMobile());

        return mapToResponse(customer);
    }


    private CustomerResponse mapToResponse(Customer customer) {

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .mobile(customer.getMobile())
                .dateOfBirth(customer.getDateOfBirth())
                .address(customer.getAddress())
                .createdAt(customer.getCreatedAt())
                .build();
    }


@Override
public CustomerResponse updateCustomer(Long id, CustomerRequest request) {

    log.info("Updating customer with ID: {}", id);

    Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("Customer not found with ID: {}", id);
                return new CustomerNotFoundException("Customer not found");
            });

    customer.setFirstName(request.getFirstName());
    customer.setLastName(request.getLastName());
    customer.setEmail(request.getEmail());
    customer.setMobile(request.getMobile());
    customer.setDateOfBirth(request.getDateOfBirth());
    customer.setAddress(request.getAddress());

    Customer updatedCustomer = customerRepository.save(customer);

    log.info("Customer updated successfully. Customer ID: {}, Email: {}",
            updatedCustomer.getCustomerId(),
            updatedCustomer.getEmail());

    return mapToResponse(updatedCustomer);
}

    @Override
    public void deleteCustomer(Long id) {

        log.info("Deleting customer with ID: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Customer not found with ID: {}", id);
                    return new CustomerNotFoundException("Customer not found");
                });

        customerRepository.delete(customer);

        log.info("Customer deleted successfully. Customer ID: {}, Email: {}",
                customer.getCustomerId(),
                customer.getEmail());
    }

}