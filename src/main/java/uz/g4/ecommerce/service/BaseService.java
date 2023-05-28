package uz.g4.ecommerce.service;

import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;
/**
 * @param <BR>> response
 * @param <CD> create and update request
 */
@Service
public interface BaseService<BR,CD> {
    BR create(CD cd);
    BR update(CD cd, UUID id);
    Boolean delete(UUID id);
    BR getById(UUID id);


}
