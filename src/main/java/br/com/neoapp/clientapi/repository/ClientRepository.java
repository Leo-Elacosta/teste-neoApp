package br.com.neoapp.clientapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.neoapp.clientapi.model.Client;

@Repository
public interface ClientRpository extends JpaRepository<Client, Long> {

}
