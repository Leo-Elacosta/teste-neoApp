package br.com.neoapp.clientapi.repository;

import br.com.neoapp.clientapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Método que o Spring Security utilizará para buscar um usuário pelo login.
     * @param login O login (username) do usuário a ser buscado.
     * @return UserDetails com os dados do usuário encontrado.
     */
    UserDetails findByLogin(String login);
}