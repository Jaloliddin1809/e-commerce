package uz.g4.ecommerce.service.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.OrderRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.order.OrderRepository;
import uz.g4.ecommerce.repository.user.UserRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class OrderService implements BaseService<BaseResponse<OrderResponse>, OrderRequest> {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
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

    public List<OrderResponse> findAll() {
        return null;
    }

}
