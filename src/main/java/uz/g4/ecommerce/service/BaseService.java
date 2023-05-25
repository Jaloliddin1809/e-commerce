package uz.g4.ecommerce.service;

import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.entity.user.UserEntity;

import java.awt.print.Pageable;
import java.beans.IntrospectionException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BaseService<BR,CD> {
    BR create(CD cd);
    BR update(CD cd);
    Boolean delete(UUID id);
    BR getById(UUID id);
    List<BR> findAll();

}
