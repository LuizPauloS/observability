package br.com.lsilva.portfolio.observability.services;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.model.entity.User;
import br.com.lsilva.portfolio.observability.repository.UserRepository;

@Service
public class UsersServices {

    Logger log = LoggerFactory.getLogger(UsersServices.class);

    private RestTemplate client;
    private UserRepository repository;

    public UsersServices(RestTemplate client, UserRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    public UserDTO addUser(UserDTO userDTO) {
        if (userDTO != null) {
            log.info("Adicionando user {} na base de dados.", userDTO.getNome());
            repository.save(new User(userDTO.getNome(), userDTO.getDocumento()));
            log.info("Call mocky.io {}", client.getForEntity(URI.create("https://run.mocky.io/v3/63b3b5f4-0d95-4693-8e27-34277df7d9e7"), Object.class));
            return userDTO;
        }
        log.error("Erro ao criar novo usuario {}...", userDTO.getNome());
        throw new RuntimeException("Erro ao criar novo usuário...");
    }

    public List<UserDTO> listAll() {
        return repository.findAll().stream().map(user -> new UserDTO(user.getNome(), user.getDocumento())).toList();
    }

    public UserDTO findByUUID(Integer id) throws Exception {
        return repository.findById(id).map(user -> new UserDTO(user.getNome(), user.getDocumento()))
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado..."));
    }
    
}
