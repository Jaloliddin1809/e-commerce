package uz.g4.ecommerce.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.response.*;
import uz.g4.ecommerce.domain.dto.request.*;
import uz.g4.ecommerce.domain.entity.user.Permission;
import uz.g4.ecommerce.domain.entity.user.Role;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.user.UserRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.*;
import java.util.stream.Collectors;

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
                .status(200)
                .build();
    }

    @Override
    public BaseResponse<UserResponse> update(UserRequest userRequest, UUID id) {
        UserEntity user = userRepository.getById(id);
        try {
            if (Objects.nonNull(userRequest.getName())) {
                user.setName(userRequest.getName());
            }
            if (Objects.nonNull(user.getUsername())) {
                user.setUsername(userRequest.getUsername());
            }
            if (Objects.nonNull(user.getUsername())) {
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            if (Objects.nonNull(user.getRoles())) {
                user.setRoles(userRequest.getRoles());
            }
            if (Objects.nonNull(user.getPermissions())) {
                user.setPermissions(userRequest.getPermissions());
            }
            userRepository.save(user);
            return BaseResponse.<UserResponse>builder()
                    .message("User updated ")
                    .status(200)
                    .data(modelMapper.map(user, UserResponse.class))
                    .build();
        } catch (Exception e) {
            return BaseResponse.<UserResponse>builder()
                    .message("User not updating ")
                    .status(400)
                    .build();
        }
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    @Override
    public BaseResponse<UserResponse> getById(UUID id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            UserResponse user = modelMapper.map(byId.get(), UserResponse.class);
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

    public Optional<UserEntity> getOneUser(UUID id) {
        return userRepository.findById(id);
    }

    public BaseResponse<List<UserResponse>> findAll(String username) {
        List<UserEntity> userEntities = userRepository.findAllExceptUser(username);
        return BaseResponse.<List<UserResponse>>builder()
                .status(200)
                .message("success")
                .data(modelMapper.map(userEntities,
                        new TypeToken<List<UserResponse>>() {
                        }.getType()))
                .build();
    }

    public BaseResponse<List<UserResponse>> findAllEmployees() {
        List<Role> excludedRoles = Arrays.asList(Role.USER);
        List<UserEntity> userEntities = userRepository.findAllUsersExceptRoles(excludedRoles);
        return BaseResponse.<List<UserResponse>>builder()
                .status(200)
                .message("success")
                .data(modelMapper.map(userEntities,
                        new TypeToken<List<UserResponse>>() {
                        }.getType()))
                .build();
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

    public BaseResponse<UserStateDto> getUserState(Long chatId) {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByChatId(chatId);
        if (userEntity.isPresent()) {
            return BaseResponse.<UserStateDto>builder()
                    .message("success")
                    .status(200)
                    .data(new UserStateDto(userEntity.get().getChatId(), userEntity.get().getUserState()))
                    .build();
        }
        return BaseResponse.<UserStateDto>builder()
                .message("fail")
                .status(400)
                .build();
    }

    public void updateState(UserStateDto userStateDto) {
        userRepository.updateUserState(userStateDto.getState(), userStateDto.getChatId());
    }


    public BaseResponse<Double> getBalance(Long chatId) {
        Optional<UserEntity> entity = userRepository.findUserEntityByChatId(chatId);
        if (entity.isPresent()) {
            return BaseResponse.<Double>builder()
                    .data(entity.get().getBalance())
                    .build();
        }
        return BaseResponse.<Double>builder()
                .data(null)
                .build();
    }

    public BaseResponse<UserResponse> fillBalance(Long chatId, String text) {
        if (text.matches("\\d+")) {
            Optional<UserEntity> userEntityByChatId = userRepository.findUserEntityByChatId(chatId);
            if (userEntityByChatId.isPresent()) {
                userRepository.updateUserBalance(Double.valueOf(text), chatId);
                return BaseResponse.<UserResponse>builder()
                        .message("balance changed")
                        .status(200)
                        .build();
            }
        }
        return BaseResponse.<UserResponse>builder()
                .message("Amount is not valid")
                .status(400)
                .build();
    }

    public BaseResponse<UserResponse> getByChatId(Long chatId) {
        Optional<UserEntity> user = userRepository.findUserEntityByChatId(chatId);

        if (user.isPresent()) {
            return BaseResponse.<UserResponse>builder()
                    .message("success")
                    .status(200)
                    .data(modelMapper.map(user.get(), UserResponse.class))
                    .build();
        }
        return BaseResponse.<UserResponse>builder()
                .message("fail")
                .status(400)
                .build();
    }

    public List<UserResponse> search(String name) {
        return userRepository.search(name).stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public void updateBalance(UUID id, double minusAmount) {
        userRepository.cutUserBalance(id, minusAmount);
    }
}
