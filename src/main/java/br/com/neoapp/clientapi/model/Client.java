package br.com.neoapp.clientapi.model;

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



}
