package uz.g4.ecommerce.service.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.OrderRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.OrderResponse;
import uz.g4.ecommerce.domain.entity.order.OrderEntity;
import uz.g4.ecommerce.repository.order.OrderRepository;
import uz.g4.ecommerce.repository.product.ProductRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements BaseService<BaseResponse<OrderResponse>, OrderRequest> {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public BaseResponse<OrderResponse> create(OrderRequest orderRequest) {

        Optional<OrderEntity> savedOrder = orderRepository.findOrderEntityByUser_IdAndProduct_Id(
                orderRequest.getUser().getId(), orderRequest.getProduct().getId());

        if (savedOrder.isPresent()) {
            savedOrder.get().setAmount(savedOrder.get().getAmount() + orderRequest.getAmount());
            orderRepository.updateAmount(savedOrder.get().getAmount(),
                    savedOrder.get().getUser().getId(),
                    savedOrder.get().getProduct().getId());
            return BaseResponse.<OrderResponse>builder()
                    .message("amount changed")
                    .status(200)
                    .build();
        } else {
            orderRepository.save(mapper.map(orderRequest, OrderEntity.class));
            return BaseResponse.<OrderResponse>builder()
                    .message("successfully saved")
                    .status(200)
                    .build();
        }
    }

    @Override
    public BaseResponse<OrderResponse> update(OrderRequest orderRequest, UUID id) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<OrderEntity> byId = orderRepository.findById(id);

        if (byId.isPresent()) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BaseResponse<OrderResponse> getById(UUID id) {
        Optional<OrderEntity> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            return BaseResponse.<OrderResponse>builder()
                    .message("success")
                    .status(200)
                    .data(mapper.map(byId, OrderResponse.class))
                    .build();
        }
        return BaseResponse.<OrderResponse>builder()
                .message("fail")
                .status(400)
                .build();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map((order) -> mapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    public List<OrderResponse> findByChatId(Long chatId) {
        try {
            List<OrderEntity> orders = orderRepository.findByChatId(chatId);

            if (!orders.isEmpty()) {
                return orders.stream()
                        .map((order) -> mapper.map(order, OrderResponse.class))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public BaseResponse<OrderResponse> removeAll(Long chatId) {
        List<OrderEntity> byChatId = orderRepository.findByChatId(chatId);

        if (byChatId.isEmpty()) {
            return BaseResponse.<OrderResponse>builder()
                    .message("You have no orders")
                    .status(400)
                    .build();
        }

        byChatId.forEach((order) -> orderRepository.deleteById(order.getId()));
        return BaseResponse.<OrderResponse>builder()
                .message("Successfully deleted")
                .status(200)
                .build();
    }

    public BaseResponse<OrderResponse> plusOrMinusOrderAmount(String orderId) {
        Optional<OrderEntity> byId = orderRepository.findById(UUID.fromString(orderId.substring(1)));

        if (byId.isPresent()) {
            if (orderId.charAt(0) == '+') {
                productRepository.updateProductAmount(1, byId.get().getProduct().getId());
                orderRepository.plusOrderAmount(byId.get().getId());
                return BaseResponse.<OrderResponse>builder()
                        .message("order amount changed")
                        .status(200)
                        .build();
            }
            if (orderId.charAt(0) == '-') {
                orderRepository.minusOrderAmount(byId.get().getId());
                if (byId.get().getAmount() == 0) {
                    orderRepository.deleteById(byId.get().getId());
                    return  BaseResponse.<OrderResponse>builder()
                            .message("order amount is 0")
                            .status(400)
                            .build();
                }
                return BaseResponse.<OrderResponse>builder()
                        .message("order amount changed")
                        .status(200)
                        .build();
            }
        }
        return BaseResponse.<OrderResponse>builder()
                .message("Order not found")
                .status(400)
                .build();
    }
}
