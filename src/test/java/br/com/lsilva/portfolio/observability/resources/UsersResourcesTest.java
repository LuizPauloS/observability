 package br.com.lsilva.portfolio.observability.resources;

 import br.com.lsilva.portfolio.observability.mock.UserMock;
 import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
 import br.com.lsilva.portfolio.observability.services.UsersServices;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.extension.ExtendWith;
 import org.springframework.http.MediaType;
 import org.springframework.test.context.junit.jupiter.SpringExtension;
 import org.springframework.test.web.servlet.MockMvc;
 import org.springframework.test.web.servlet.setup.MockMvcBuilders;

 import static org.mockito.ArgumentMatchers.any;
 import static org.mockito.Mockito.mock;
 import static org.mockito.Mockito.when;
 import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
 import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
 import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 @ExtendWith(SpringExtension.class)
 class UsersResourcesTest {

     private MockMvc mockMvc;
     private UsersServices userService;
     private UsersResources userResource;
     private ObjectMapper objectMapper;

     @BeforeEach
     public void setUp() {
         this.objectMapper = new ObjectMapper();
         this.userService = mock(UsersServices.class);
         this.userResource = new UsersResources(userService);
         this.mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
     }

     @Test
     void deveAdicionarNovoUsuarioComSucesso() throws Exception {
         UserDTO userDTO = UserMock.getNewUserDTOMock();
         when(userService.addUser(any())).thenReturn(userDTO);
         mockMvc.perform(post("/users")
                      .contentType(MediaType.APPLICATION_JSON_VALUE)
                      .content(objectMapper.writeValueAsString(userDTO)))
                 .andDo(print())
                 .andExpect(status().isOk())
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(jsonPath("$.nome").value("Luiz"))
                 .andReturn();
     }

     @Test
     void deveOcorrerErroAoTentarAdicionarNovoUsuario() throws Exception {
         mockMvc.perform(post("/users")
                         .contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andDo(print())
                 .andExpect(status().isBadRequest())
                 .andReturn();
     }

     @Test
     void deveOcorrerErroAoTentarAdicionarNovoUsuarioSemInformarDados() throws Exception {
         mockMvc.perform(post("/users")
                         .contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andDo(print())
                 .andExpect(status().isBadRequest())
                 .andReturn();
     }
 }
