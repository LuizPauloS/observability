package br.com.lsilva.portfolio.observability.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    
    @Autowired
    private UsersServices service;

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.addUser(userDTO));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserDTO> findByUUID(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(service.findByUUID(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(service.deleteUser(id));
    }
}
