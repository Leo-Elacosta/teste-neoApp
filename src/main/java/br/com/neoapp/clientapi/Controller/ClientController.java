package br.com.neoapp.clientapi.Controller;

import br.com.neoapp.clientapi.dtos.ClientRequestDTO;
import br.com.neoapp.clientapi.dtos.ClientResponseDTO;
import br.com.neoapp.clientapi.service.ClientService;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clients")

public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO clientDTO) {
        ClientResponseDTO newClient = service.createClient(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newClient.id())
                .toUri();
        return ResponseEntity.created(uri).body(newClient);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> findByName(
            @RequestParam String name,
            @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        Page<ClientResponseDTO> clients = service.findByName(name, pageable);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        ClientResponseDTO client = service.findById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponseDTO>> list(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ClientResponseDTO> clientes = service.clientList(pageable);
        return ResponseEntity.ok(clientes);
    }

     @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO clienteDTO) {
        ClientResponseDTO clienteAtualizado = service.updateClient(id, clienteDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }


}