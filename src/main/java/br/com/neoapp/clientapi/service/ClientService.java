package br.com.neoapp.clientapi.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.neoapp.clientapi.dtos.ClientRequestDTO;
import br.com.neoapp.clientapi.dtos.ClientResponseDTO;
import br.com.neoapp.clientapi.model.Client;
import br.com.neoapp.clientapi.repository.ClientRepository;

@Service
public class ClientService {
    
    @Autowired
    private ClientRepository repository;

    //calcula idade
    private Integer calculateAge(LocalDate bornDate) {
        if (bornDate == null) {
            return 0;
        }
        return Period.between(bornDate, LocalDate.now()).getYears();
    }

    //Converter entidade para DTO resposta
    private ClientResponseDTO toResponseDTO(Client client) {
        return new ClientResponseDTO(
            client.getId(),
            client.getName(),
            client.getCpf(),
            client.getBornDate(),
            client.getEmail(),
            client.getPhone(),
            calculateAge(client.getBornDate())
        );
    }

    public ClientResponseDTO createClient(ClientRequestDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.name());
        client.setCpf(clientDTO.cpf());
        client.setBornDate(clientDTO.bornDate());
        client.setEmail(clientDTO.email());
        client.setPhone(clientDTO.phone());

        Client savedClient = repository.save(client);
        return toResponseDTO(savedClient);
    }

    //adicionado buscar por nome
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findByName(String nome, Pageable pageable) {
        Page<Client> clients = repository.findByNameContainingIgnoreCase(nome, pageable);
        return clients.map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> clientList(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return clients.map(this::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public ClientResponseDTO findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado pelo ID: " + id));
        return toResponseDTO(client);
    }

    @Transactional
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientDTO) {
        Client existingClient = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado pelo ID: " + id));

        existingClient.setName(clientDTO.name());
        existingClient.setCpf(clientDTO.cpf());
        existingClient.setBornDate(clientDTO.bornDate());
        existingClient.setEmail(clientDTO.email());
        existingClient.setPhone(clientDTO.phone());

        Client updatedClient = repository.save(existingClient);
        return toResponseDTO(updatedClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado pelo ID: " + id);
        }
        repository.deleteById(id);
    }
}

