package br.com.neoapp.clientapi.dtos;
import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record ClientRequestDTO (
    
    @NotBlank(message = "Name is mandatory")
    String name,
    
    @NotBlank(message = "CPF is mandatory")
    @CPF(message = "Invalid CPF")
    String cpf,
    
    @NotNull(message = "Born date is mandatory")
    @Past(message = "Born date must be in the past")
    LocalDate bornDate,
    
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    String email,

    @NotBlank(message = "Phone is mandatory")
    String phone
) {}
