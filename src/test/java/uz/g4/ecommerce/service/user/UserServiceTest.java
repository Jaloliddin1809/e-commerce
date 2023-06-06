package uz.g4.ecommerce.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.user.UserRepository;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void create() {
        String username = "jack";
        UserRequest user = UserRequest.builder()
                .name("imom")
                .username(username)
                .password("password")
                .balance(0d)
                .build();
        UserEntity entiy = UserEntity.builder()
                .name("imom")
                .username(username)
                .password("password")
                .balance(0d)
                .build();

        when(mapper.map(user, UserEntity.class)).thenReturn(entiy);
        entiy.setPassword(when(encoder.encode(entiy.getPassword())).thenReturn("password").toString());

        userRepository.save(entiy);

        boolean exists = userRepository.existsByUsername(username);

        assertTrue(exists);
    }

    @Test
    void deleteById() {
        Optional<UserEntity> user = Optional.ofNullable(UserEntity.builder()
                .username("imom")
                .name("name")
                .password("password")
                .build());

        userRepository.save(user.get());

        doReturn(user).when(userRepository).findById(user.get().getId());

        assertNotNull(userRepository.findById(user.get().getId()));
    }

    @Test
    void getById() {
        UserEntity user = UserEntity.builder()
                .username("imom")
                .name("name")
                .password("password")
                .build();

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(mapper.map(user, UserResponse.class)).thenReturn(userResponse);

        BaseResponse<UserResponse> response = userService.getById(UUID.randomUUID());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
    }
}