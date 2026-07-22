package com.bank.customerservice.service;

import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    Page<CustomerResponse> getAllCustomers(int page,
                                           int size,
                                           String sortBy,
                                           String direction);

    CustomerResponse getCustomerById(Long id);

    CustomerResponse getCustomerByCustomerId(String customerId);


    CustomerResponse getCustomerByEmail(String email);

    CustomerResponse getCustomerByMobile(String mobile);

    CustomerResponse updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);
}