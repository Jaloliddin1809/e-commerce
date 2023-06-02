package uz.g4.ecommerce.service.category;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uz.g4.ecommerce.domain.dto.request.CategoryRequest;
import uz.g4.ecommerce.domain.dto.response.BaseResponse;
import uz.g4.ecommerce.domain.dto.response.CategoryResponse;
import uz.g4.ecommerce.domain.dto.response.UserResponse;
import uz.g4.ecommerce.domain.dto.response.ProductResponse;
import uz.g4.ecommerce.domain.entity.category.CategoryEntity;
import uz.g4.ecommerce.domain.entity.user.UserEntity;
import uz.g4.ecommerce.repository.category.CategoryRepository;
import uz.g4.ecommerce.service.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<BaseResponse<CategoryResponse>, CategoryRequest> {
    private final CategoryRepository repository;
    private final ModelMapper mapper;

    @Override
    public BaseResponse<CategoryResponse> create(CategoryRequest categoryRequest) {
        CategoryEntity category = new CategoryEntity();
        category.setType(categoryRequest.getType());

        if (categoryRequest.getParentId() != null) {
            Optional<CategoryEntity> byId = repository.findById(categoryRequest.getParentId());
            if (byId.isPresent()) {
                try {
                    category.setParent(byId.get());
                    repository.save(category);
                    return BaseResponse.<CategoryResponse>builder()
                            .message("Child category added")
                            .status(200)
                            .build();
                } catch (DataIntegrityViolationException e) {
                    return BaseResponse.<CategoryResponse>builder()
                            .status(400)
                            .message("Category name already user")
                            .build();
                }
            }
        }
        category.setParent(null);
        repository.save(category);
        return BaseResponse.<CategoryResponse>builder()
                .message("Parent category added")
                .status(200)
                .build();
    }

    @Override
    public BaseResponse<CategoryResponse> update(CategoryRequest categoryRequest, UUID id) {
        Optional<CategoryEntity> category = repository.findById(id);
        if (category.isPresent()) {
            if (categoryRequest.getParentId() != null) {
                category.get().setParent(repository.findById(categoryRequest.getParentId()).get());
                category.get().setType(categoryRequest.getType());
                repository.save(category.get());
                return BaseResponse.<CategoryResponse>builder()
                        .message("Success")
                        .status(200)
                        .data(mapper.map(category, CategoryResponse.class))
                        .build();
            }
            category.get().setType(categoryRequest.getType());
            repository.save(category.get());
            return BaseResponse.<CategoryResponse>builder()
                    .message("Success")
                    .status(200)
                    .data(mapper.map(category, CategoryResponse.class))
                    .build();
        }
        return BaseResponse.<CategoryResponse>builder()
                .message("Category not found")
                .status(404)
                .build();
    }

    @Override
    public Boolean delete(UUID id) {
        Optional<CategoryEntity> byId = repository.findById(id);
        if (byId.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BaseResponse<CategoryResponse> getById(UUID id) {
        Optional<CategoryEntity> byId = repository.findById(id);

        if (byId.isPresent()) {
          return BaseResponse.<CategoryResponse>builder()
                  .data(mapper.map(byId.get(), CategoryResponse.class))
                  .message("success")
                  .status(200)
                  .build();
        }
        return BaseResponse.<CategoryResponse>builder()
                .message("fail")
                .status(400)
                .build();
    }

    public BaseResponse<List<CategoryResponse>> findAll() {
        List<CategoryEntity> list =repository.findAll();
        return BaseResponse.<List<CategoryResponse>>builder()
                .status(200)
                .message("success")
                .data(mapper.map(list,
                        new TypeToken<List<CategoryResponse>>(){}.getType()))
                .build();
    }
    public BaseResponse<List<CategoryResponse>> findAllChildCategories() {
        List<CategoryEntity> list =repository.findByParentIdNotNull();
        return BaseResponse.<List<CategoryResponse>>builder()
                .status(200)
                .message("success")
                .data(mapper.map(list,
                        new TypeToken<List<CategoryResponse>>(){}.getType()))
                .build();
    }

    public List<CategoryResponse> getParentCategories() {
        return repository.findByParentCategory().stream()
                .map((category -> mapper.map(category, CategoryResponse.class)))
                .collect(Collectors.toList());
    }

    public List<CategoryResponse> getChildCategories(UUID uuid) {
        List<CategoryEntity> childCategoriesByParentId = repository.findCategoryEntityByParentId(uuid);

        if (childCategoriesByParentId == null) {
            return null;
        }

        return childCategoriesByParentId.stream()
                .map((category -> mapper.map(category, CategoryResponse.class)))
                .collect(Collectors.toList());
    }

    public BaseResponse<CategoryResponse> findByCategoryName(String category) {
        Optional<CategoryEntity> categoryEntityByType = repository.findByType(category);

        if (categoryEntityByType.isPresent()) {
            return BaseResponse.<CategoryResponse>builder()
                    .data(mapper.map(categoryEntityByType.get(), CategoryResponse.class))
                    .message("success")
                    .status(200)
                    .build();
        }
        return BaseResponse.<CategoryResponse>builder()
                .message("fail")
                .status(400)
                .build();
    }

}
