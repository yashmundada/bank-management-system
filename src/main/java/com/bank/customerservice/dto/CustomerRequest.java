package com.bank.customerservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits")
    private String mobile;

    private LocalDate dateOfBirth;

    private String address;
}