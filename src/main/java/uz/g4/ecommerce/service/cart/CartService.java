package uz.g4.ecommerce.service.cart;

import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.UUID;

@Service
public class CartService implements BaseService<BaseResponse<CartResponse>, CartRequest> {
    @Override
    public BaseResponse<CartResponse> create(CartRequest cartRequest) {
        return null;
    }

    @Override
    public BaseResponse<CartResponse> update(CartRequest cartRequest) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<CartResponse> getById(UUID id) {
        return null;
    }

    @Override
    public List<BaseResponse<CartResponse>> findAll() {
        return null;
    }

}
