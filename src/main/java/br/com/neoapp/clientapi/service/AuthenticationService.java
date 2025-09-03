package br.com.neoapp.clientapi.service;

import br.com.neoapp.clientapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Esta classe é o serviço de autenticação do Spring Security.
 * A anotação @Service a torna um Bean gerenciado pelo Spring, permitindo
 * que o Spring Security a encontre e utilize automaticamente.
 */
@Service
public class AuthenticationService implements UserDetailsService {

    // Injetamos o repositório de usuários para poder consultar o banco de dados.
    @Autowired
    private UserRepository repository;

    /**
     * Este método é chamado pelo Spring Security quando um usuário tenta se autenticar.
     * Ele recebe o nome de usuário (login) e deve retornar um objeto UserDetails
     * com os dados do usuário, se ele for encontrado.
     *
     * @param username o nome de usuário (login) que está tentando se autenticar.
     * @return um objeto UserDetails contendo os dados do usuário.
     * @throws UsernameNotFoundException se o usuário não for encontrado no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usamos o método do nosso repositório para buscar o usuário pelo login.
        UserDetails user = repository.findByLogin(username);

        // Se o usuário não for encontrado, o Spring Security precisa ser informado.
        // O ideal é lançar a exceção UsernameNotFoundException, mas para simplificar,
        // o Spring Security também lida bem se o método retornar null, tratando como
        // credencial inválida.
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        return user;
    }
}