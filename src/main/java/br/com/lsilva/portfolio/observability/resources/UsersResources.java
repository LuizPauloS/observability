package br.com.lsilva.portfolio.observability.resources;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.services.UsersServices;
import io.micrometer.observation.annotation.Observed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Observed(contextualName = "usersResources")
@RestController
@RequestMapping("/users")
public class UsersResources {

    Logger log = LoggerFactory.getLogger(UsersResources.class);
    

    private final UsersServices service;

    public UsersResources(UsersServices service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Validated UserDTO userDTO) throws IOException {
        return ResponseEntity.ok(service.addUser(userDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<UserDTO> findByUUID(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(service.findByUUID(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteUser(id));
    }
}
