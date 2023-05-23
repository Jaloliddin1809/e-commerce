package uz.g4.ecommerce.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.UserLoginDto;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.domain.exception.DataNotFoundException;
import uz.g4.ecommerce.repository.UserRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<BaseResponse<UserResponse>, UserRequest> {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public BaseResponse<UserResponse> create(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return BaseResponse.<UserResponse>builder()
                    .message("Already exist by "+ userRequest.getUsername())
                    .status(400)
                    .build();
        }
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return BaseResponse.<UserResponse>builder()
                .message("Success")
                .data(modelMapper.map(user,UserResponse.class))
                .status(201)
                .build();
    }

    @Override
    public BaseResponse<UserResponse> update(UserRequest userRequest) {
        return null;
    }

    @Override
    public BaseResponse<UserResponse> delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<UserResponse> getById(UUID id) {
        return null;
    }

    @Override
    public List<BaseResponse<UserResponse>> getAll() {
        return null;
    }
    public BaseResponse<UserResponse> login(UserLoginDto auth) {
        UserEntity userEntity = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new DataNotFoundException("username/password is wrong"));
        if(passwordEncoder.matches(auth.getPassword(), userEntity.getPassword())) {
            BaseResponse.<UserResponse>builder()
                    .message("Success")
                    .data(modelMapper.map(userEntity,UserResponse.class))
                    .status(200)
                    .build();
        }
        throw new DataNotFoundException("username/password is wrong");
    }

}
