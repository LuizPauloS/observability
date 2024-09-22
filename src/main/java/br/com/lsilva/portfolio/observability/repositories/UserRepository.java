package br.com.lsilva.portfolio.observability.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.lsilva.portfolio.observability.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
