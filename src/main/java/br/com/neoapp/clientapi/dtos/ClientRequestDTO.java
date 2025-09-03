package br.com.neoapp.clientapi.dtos;
import java.time.LocalDate;

public record ClientRequestDTO (
    String name,
    String cpf,
    LocalDate bornDate,
    String email,
    String phone
) {}
