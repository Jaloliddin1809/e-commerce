package uz.g4.ecommerce.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() {
        String username = "jack";

        UserEntity user = UserEntity.builder()
                .username(username)
                .name("h2 name")
                .password("password")
                .balance(0d)
                .build();

        userRepository.save(user);

        Optional<UserEntity> byUsername = userRepository.findByUsername(username);

        assertTrue(byUsername.isPresent());
    }


}