package uz.g4.ecommerce.service;

import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;

import java.util.List;
import java.util.UUID;

@Service
public interface BaseService<BR,CD> {
    BR create(CD cd);
    BR update(CD cd);
    Boolean delete(UUID id);
    BR getById(UUID id);


}
