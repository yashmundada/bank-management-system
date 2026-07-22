
package com.bank.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponse {

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private LocalDate dateOfBirth;
    private String address;
    private LocalDateTime createdAt;
}