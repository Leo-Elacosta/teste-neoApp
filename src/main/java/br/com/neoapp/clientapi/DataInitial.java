package br.com.neoapp.clientapi;

import br.com.neoapp.clientapi.model.User;
import br.com.neoapp.clientapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitial implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário 'user' já existe para não criá-lo duas vezes
        if (userRepository.findByLogin("user") == null) {
            User adminUser = new User();
            adminUser.setLogin("user");
            // Criptografa a senha '123456' usando o PasswordEncoder do Spring
            adminUser.setPassword(passwordEncoder.encode("123456"));
            
            userRepository.save(adminUser);
            System.out.println(">>> Usuário 'user' criado com sucesso! <<<");
        } else {
            System.out.println(">>> Usuário 'user' já existe. Nenhuma ação necessária. <<<");
        }
    }
}