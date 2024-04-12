package capstone.communityservice.domain.category.controller;


import capstone.communityservice.domain.category.dto.CategoryCreateRequestDto;
import capstone.communityservice.domain.category.dto.CategoryDeleteRequestDto;
import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.category.dto.CategoryUpdateRequestDto;
import capstone.communityservice.domain.category.service.CategoryCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryCommandController {

    private CategoryCommandService categoryCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@Valid @RequestBody CategoryCreateRequestDto requestDto){
        categoryCommandService.create(requestDto);

        return DataResponseDto.of("Category create success!");
    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody CategoryUpdateRequestDto requestDto){
        categoryCommandService.update(requestDto);

        return DataResponseDto.of("Category update success!");
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody CategoryDeleteRequestDto requestDto){
        categoryCommandService.delete(requestDto);

        return DataResponseDto.of("Category update success!");
    }
}
