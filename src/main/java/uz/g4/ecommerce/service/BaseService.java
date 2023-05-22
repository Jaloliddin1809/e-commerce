package uz.g4.ecommerce.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BaseService<BR,CD> {
    BR create(CD cd);
    BR update(CD cd);
    BR delete(UUID id);
    BR getById(UUID id);
    List<BR> getAll();

}
