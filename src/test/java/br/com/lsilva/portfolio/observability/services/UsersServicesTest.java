package br.com.lsilva.portfolio.observability.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.model.entity.User;
import br.com.lsilva.portfolio.observability.repository.UserRepository;

@ExtendWith(SpringExtension.class)
class UsersServicesTest {

    RestTemplate client;
    UsersServices service;
    UserRepository repository;
    final String uriMockTest = "https://run.mocky.io/v3/63b3b5f4-0d95-4693-8e27-34277df7d9e7";

    @BeforeEach
    void setUp() {
        this.client = mock(RestTemplate.class);
        this.repository = mock(UserRepository.class);
        this.service = new UsersServices(client, repository, uriMockTest);
    }

    @Test
    void dadoUserDTO_quandoChamarAddUser_deveSalvarComSucesso() {
        User user = new User("Joao", "123.456.789-10");
        UserDTO userDTO = new UserDTO(user.getNome(), user.getDocumento());

        when(repository.save(any())).thenReturn(user);
        UserDTO newUserDTO = service.addUser(userDTO);

        Assertions.assertNotNull(newUserDTO);
        Assertions.assertEquals(userDTO.getNome(), newUserDTO.getNome());
        Assertions.assertEquals(userDTO.getDocumento(), newUserDTO.getDocumento());
    }

    @Test
    void quandoChamarListAll_deveRetornarListaComSucesso() {
        User user1 = new User("Carol", "222.456.789-10");
        User user2 = new User("Maria", "333.456.789-10");
        User user3 = new User("Lucas", "444.456.789-10");

        when(repository.findAll()).thenReturn(List.of(user1, user2, user3));
        List<UserDTO> listUserDTOs = service.listAll();

        Assertions.assertNotNull(listUserDTOs);
        Assertions.assertFalse(listUserDTOs.isEmpty());
    }

    @Test
    void quandoChamarFindByUUID_deveRetornarUserComSucesso() throws Exception {
        User user = new User("Carol", "222.456.789-10");

        when(repository.findById(1)).thenReturn(Optional.of(user));
        UserDTO userDTO = service.findByUUID(1);

        Assertions.assertNotNull(userDTO);
    }

}
