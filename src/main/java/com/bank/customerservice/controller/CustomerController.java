package com.bank.customerservice.controller;

import com.bank.customerservice.dto.ApiResponse;
import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import com.bank.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse customer = customerService.createCustomer(request);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                customer
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search/customerId/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByCustomerId(
            @PathVariable String customerId) {

        CustomerResponse customer = customerService.getCustomerByCustomerId(customerId);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer fetched successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByEmail(
            @PathVariable String email) {

        CustomerResponse customer = customerService.getCustomerByEmail(email);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer fetched successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/mobile/{mobile}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByMobile(
            @PathVariable String mobile) {

        CustomerResponse customer = customerService.getCustomerByMobile(mobile);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer fetched successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getAllCustomers(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "customerId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<CustomerResponse> customers =
                customerService.getAllCustomers(page, size, sortBy, direction);

        ApiResponse<Page<CustomerResponse>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customers fetched successfully",
                customers
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @PathVariable Long id) {

        CustomerResponse customer = customerService.getCustomerById(id);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer fetched successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse customer = customerService.updateCustomer(id, request);

        ApiResponse<CustomerResponse> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer updated successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }
}