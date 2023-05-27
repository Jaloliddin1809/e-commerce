package uz.g4.ecommerce.service.product;

import jakarta.persistence.criteria.Order;
import uz.g4.ecommerce.domain.dto.request.ProductRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.UUID;

public class ProductService implements BaseService<BaseResponse<ProductResponse>, ProductRequest> {
    @Override
    public BaseResponse<ProductResponse> create(ProductRequest productRequest) {
        return null;
    }

    @Override
    public BaseResponse<ProductResponse> update(ProductRequest productRequest) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<ProductResponse> getById(UUID id) {
        return null;
    }

    public List<OrderResponse> findAll() {
        return null;
    }

}
