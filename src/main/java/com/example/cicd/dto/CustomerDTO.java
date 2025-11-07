package com.example.cicd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    // optional - if not provided we'll set current date on creation
    private LocalDate registerDate;

    public CustomerDTO() {}

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public LocalDate getRegisterDate() { return registerDate; }

    public void setRegisterDate(LocalDate registerDate) { this.registerDate = registerDate; }
}
