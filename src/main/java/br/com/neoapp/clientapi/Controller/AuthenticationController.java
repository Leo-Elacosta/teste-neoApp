package br.com.neoapp.clientapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neoapp.clientapi.dtos.AuthenticationDTO;
import br.com.neoapp.clientapi.dtos.LoginResponseDTO;
import br.com.neoapp.clientapi.security.TokenService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

   @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dados) {
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var authentication = manager.authenticate(authToken);

        var token = tokenService.createToken((UserDetails) authentication.getPrincipal());
    
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}