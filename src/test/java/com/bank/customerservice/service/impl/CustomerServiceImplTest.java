package com.bank.customerservice.service.impl;

import com.bank.customerservice.dto.CustomerRequest;
import com.bank.customerservice.dto.CustomerResponse;
import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.exception.DuplicateCustomerException;
import com.bank.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void createCustomer_ShouldCreateCustomerSuccessfully() {

        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Rohit");
        request.setLastName("Sharma");
        request.setEmail("rohit@gmail.com");
        request.setMobile("9876543210");
        request.setDateOfBirth(LocalDate.of(1998, 5, 20));
        request.setAddress("Pune");

        Customer customer = Customer.builder()
                .id(1L)
                .customerId("CUST-123456")
                .firstName("Rohit")
                .lastName("Sharma")
                .email("rohit@gmail.com")
                .mobile("9876543210")
                .dateOfBirth(LocalDate.of(1998, 5, 20))
                .address("Pune")
                .createdAt(LocalDateTime.now())
                .build();

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(customerRepository.existsByMobile(request.getMobile()))
                .thenReturn(false);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        // Act
        CustomerResponse response = customerService.createCustomer(request);

        // Assert
        assertNotNull(response);
        assertEquals("Rohit", response.getFirstName());
        assertEquals("Sharma", response.getLastName());
        assertEquals("rohit@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getMobile());

        verify(customerRepository).existsByEmail(request.getEmail());
        verify(customerRepository).existsByMobile(request.getMobile());
        verify(customerRepository).save(any(Customer.class));
    }
    @Test
    void createCustomer_ShouldThrowException_WhenEmailAlreadyExists() {

        // Arrange
        CustomerRequest request = new CustomerRequest();
        request.setFirstName("Rohit");
        request.setLastName("Sharma");
        request.setEmail("rohit@gmail.com");
        request.setMobile("9876543210");
        request.setDateOfBirth(LocalDate.of(1998, 5, 20));
        request.setAddress("Pune");

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        // Act & Assert
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(request)
        );

        assertEquals("Email already exists", exception.getMessage());

        // Verify
        verify(customerRepository).existsByEmail(request.getEmail());
        verify(customerRepository, never()).save(any(Customer.class));
    }
}