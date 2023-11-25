package br.com.lsilva.portfolio.observability.mock;

import br.com.lsilva.portfolio.observability.model.dto.UserDTO;
import br.com.lsilva.portfolio.observability.model.entity.User;

import java.util.List;

public class UserMock {

    public static List<User> getListUserMock() {
        User user1 = getUserMock("Carol", "222.456.789-10");
        User user2 = getUserMock("Maria", "333.456.789-10");
        User user3 = getUserMock("Lucas", "444.456.789-10");
        return List.of(user1, user2, user3);
    }

    public static User getUserMock(String nome, String documento) {
        return new User(nome, documento);
    }

    public static User getNewUserMock() {
        return getUserMock("Luiz", "333.333.333-33");
    }

    public static UserDTO getNewUserDTOMock() {
        return new UserDTO("Luiz", "333.333.333-33");
    }


}
