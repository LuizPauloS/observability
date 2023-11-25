package br.com.lsilva.portfolio.observability.services;

import br.com.lsilva.portfolio.observability.client.MockyClient;
import br.com.lsilva.portfolio.observability.mock.UserMock;
import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.model.entity.User;
import br.com.lsilva.portfolio.observability.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UsersServicesTest {

    RestTemplate client;
    MockyClient mockyClient;
    UsersServices service;
    UserRepository repository;
    final String uriMockTest = "https://run.mocky.io/v3/63b3b5f4-0d95-4693-8e27-34277df7d9e7";

    @BeforeEach
    void setUp() {
        this.client = mock(RestTemplate.class);
        this.mockyClient = mock(MockyClient.class);
        this.repository = mock(UserRepository.class);
        this.service = new UsersServices(client, mockyClient, repository, uriMockTest);
    }

    @Test
    void dadoUserDTO_quandoChamarAddUser_deveSalvarComSucesso() {
         UserDTO userDTO = UserMock.getNewUserDTOMock();
         User user = UserMock.getUserMock(userDTO.getNome(), userDTO.getDocumento());

         when(repository.save(any())).thenReturn(user);
         UserDTO userResponse = service.addUser(userDTO);

         assertNotNull(userResponse);
         assertEquals(userDTO.getNome(), userResponse.getNome());
         assertEquals(userDTO.getDocumento(), userResponse.getDocumento());
    }

    @Test
    void quandoChamarListAll_deveRetornarListaComSucesso() {
        List<User> userLists = UserMock.getListUserMock();

        when(repository.findAll()).thenReturn(userLists);
        List<UserDTO> listUserResponse = service.listAll();

        assertNotNull(listUserResponse);
        assertFalse(listUserResponse.isEmpty());
        assertEquals(listUserResponse.size(), userLists.size());
    }

    @Test
    void quandoChamarFindByUUID_deveRetornarUserComSucesso() {
        User user = UserMock.getNewUserMock();

        when(repository.findById(1)).thenReturn(Optional.of(user));
        UserDTO userResponse = service.findByUUID(1);

        assertNotNull(userResponse);
    }

    @Test
    void quandoChamarDeleteUser_deveDeletarERetornarSucesso() {

        when(repository.existsById(1)).thenReturn(Boolean.TRUE);
        Boolean userDeleted = service.deleteUser(1);

        assertTrue(userDeleted);
    }

}
