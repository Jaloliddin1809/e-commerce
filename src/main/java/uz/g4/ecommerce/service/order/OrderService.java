package uz.g4.ecommerce.service.order;

import uz.g4.ecommerce.domain.dto.request.OrderRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.UUID;

public class OrderService implements BaseService<BaseResponse<OrderResponse>, OrderRequest> {
    @Override
    public BaseResponse<OrderResponse> create(OrderRequest orderRequest) {
        return null;
    }

    @Override
    public BaseResponse<OrderResponse> update(OrderRequest orderRequest, UUID id) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<OrderResponse> getById(UUID id) {
        return null;
    }

    @Override
    public List<BaseResponse<OrderResponse>> findAll() {
        return null;
    }

}
