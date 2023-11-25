package br.com.lsilva.portfolio.observability.services;

import br.com.lsilva.portfolio.observability.client.MockyClient;
import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.model.entity.User;
import br.com.lsilva.portfolio.observability.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServices {

    Logger log = LoggerFactory.getLogger(UsersServices.class);

    private final RestTemplate client;
    private final MockyClient mockyClient;
    private final UserRepository repository;
    private final String uriMock;

    public UsersServices(RestTemplate client,  MockyClient mockyClient,
                         UserRepository repository, @Value("${mocky.io.url}") String uriMock) {
        this.client = client;
        this.mockyClient = mockyClient;
        this.repository = repository;
        this.uriMock = uriMock;
    }

    public UserDTO addUser(UserDTO userDTO) {
        log.info("Adicionando user {} na base de dados.", userDTO.getNome());
        User userResponse = repository.save(new User(userDTO.getNome(), userDTO.getDocumento()));
        return Optional.of(userResponse).map(user -> new UserDTO(userDTO.getNome(), user.getDocumento()))
            .orElseThrow(() -> {
                log.error("Erro ao criar novo usuario {}...", userDTO.getNome());
                return new RuntimeException("Erro ao criar novo usuário: " + userDTO.getNome());
            });
    }

    public List<UserDTO> listAll() {
        return repository.findAll().stream().map(user -> new UserDTO(user.getNome(), user.getDocumento())).toList();
    }

    public UserDTO findByUUID(Integer id) {
        return repository.findById(id).map(user -> new UserDTO(user.getNome(), user.getDocumento()))
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado..."));
    }

    public Boolean deleteUser(Integer id) {
        boolean existsById = repository.existsById(id);
        if (existsById) repository.deleteById(id);
        return existsById;
    }
    
}
