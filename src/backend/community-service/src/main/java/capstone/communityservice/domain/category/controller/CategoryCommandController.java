package capstone.communityservice.domain.category.controller;


import capstone.communityservice.domain.category.dto.request.CategoryCreateRequest;
import capstone.communityservice.domain.category.dto.request.CategoryDeleteRequest;
import capstone.communityservice.domain.category.dto.response.CategoryResponse;
import capstone.communityservice.domain.category.dto.request.CategoryUpdateRequest;
import capstone.communityservice.domain.category.service.CategoryCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryCommandController {

    private final CategoryCommandService categoryCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@Valid @RequestBody CategoryCreateRequest request){
        CategoryResponse response = categoryCommandService.create(request);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody CategoryUpdateRequest request){
        CategoryResponse response = categoryCommandService.update(request);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody CategoryDeleteRequest request){
        categoryCommandService.delete(request);

        return DataResponseDto.of("Category delete success!");
    }
}
