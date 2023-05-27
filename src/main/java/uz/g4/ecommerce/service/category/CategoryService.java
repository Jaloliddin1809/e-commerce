package uz.g4.ecommerce.service.category;

import uz.g4.ecommerce.domain.dto.request.CategoryRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<BaseResponse<CategoryResponse>, CategoryRequest> {
    @Override
    public BaseResponse<CategoryResponse> create(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public BaseResponse<CategoryResponse> update(CategoryRequest categoryRequest, UUID id) {
        return null;
    }

    @Override
    public Boolean delete(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<CategoryResponse> getById(UUID id) {
        return null;
    }

    @Override
    public List<BaseResponse<CategoryResponse>> findAll() {
        return null;
    }

}
