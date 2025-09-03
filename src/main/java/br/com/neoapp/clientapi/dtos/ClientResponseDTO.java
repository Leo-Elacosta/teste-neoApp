package br.com.neoapp.clientapi.dtos;

import java.time.LocalDate;

public record ClientResponseDTO (
    Long id,
    String name,
    String cpf,
    LocalDate bornDate,
    String email,
    String phone,
    Integer age
){}
