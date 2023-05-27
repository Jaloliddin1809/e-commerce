package uz.g4.ecommerce.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.UserLoginRequest;
import uz.g4.ecommerce.domain.dto.request.UserRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.user.UserRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.*;

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
                    .message("Already exist by " + userRequest.getUsername())
                    .status(400)
                    .build();
        }
        UserEntity user = modelMapper.map(userRequest, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return BaseResponse.<UserResponse>builder()
                .message("Success")
                .data(modelMapper.map(user, UserResponse.class))
                .status(201)
                .build();
    }

    @Override
    public BaseResponse<UserResponse> update(UserRequest userRequest) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setEnabled(false);
            userRepository.save(user.get());
            return true;
        }
        return false;


    }

    @Override
    public BaseResponse<UserResponse> getById(UUID id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            UserResponse user = modelMapper.map(byId, UserResponse.class);
            return BaseResponse.<UserResponse>builder()
                    .message("Success")
                    .status(200)
                    .data(user)
                    .build();
        }
        return BaseResponse.<UserResponse>builder()
                .message("User not found")
                .status(404)
                .build();
    }

    public List<UserResponse> findAll() {

        return null;
    }

    public List<UserEntity> findByPage(Optional<Integer> page, Optional<Integer> size) {
        if (page.isPresent()) {
            if (size.isPresent()) {
                return userRepository.findAll(PageRequest.of(page.get(), size.get())).getContent();
            }
            return userRepository.findAll(PageRequest.of(page.get(), 10)).getContent();
        }
        return size.map(integer -> userRepository.findAll(PageRequest.of(0, integer)).getContent()).orElseGet(userRepository::findAll);

    }

    public BaseResponse<UserResponse> login(UserLoginRequest auth) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(auth.getUsername());
        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            if (passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
               return BaseResponse.<UserResponse>builder()
                        .message(auth.getUsername() + " successfully signed")
                        .data(modelMapper.map(userEntity, UserResponse.class))
                        .status(200)
                        .build();
            }
        }
        return BaseResponse.<UserResponse>builder()
                .message("username/password is wrong")
                .status(400)
                .build();
    }

    public Boolean addRole(UUID id, Role role) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            Set<Role> roles = user.get().getRoles();
            roles.add(role);
            user.get().setRoles(roles);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public boolean addPermission(UUID id, Permission permission) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            Set<Permission> permissions = byId.get().getPermissions();
            permissions.add(permission);
            byId.get().setPermissions(permissions);
            userRepository.save(byId.get());
            return true;
        }
        return false;
    }
}
