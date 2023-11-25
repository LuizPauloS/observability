package br.com.lsilva.portfolio.observability.client;

import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import feign.Response;

@FeignClient(name = "api-observability", url = "${mocky.io.url}")
public interface MockyClient {
    
    @GetMapping
    UserDTO getMockyIO();
    //ResponseEntity<Object> getMockyIO();
}
