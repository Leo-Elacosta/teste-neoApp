package br.com.neoapp.clientapi.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Client {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate bornDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;
    
}
