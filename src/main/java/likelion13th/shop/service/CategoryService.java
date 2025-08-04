package likelion13th.shop.service;

import jakarta.transaction.Transactional;
import likelion13th.shop.DTO.request.CategoryRequest;
import likelion13th.shop.DTO.response.CategoryResponseDto;
import likelion13th.shop.domain.Category;
import likelion13th.shop.domain.User;
import likelion13th.shop.domain.Order;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.repository.CategoryRepository;
import likelion13th.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    //모든카테고리조회
    @Transactional
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }

    //개별카테고리조회
    @Transactional
    public CategoryResponseDto getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).map(CategoryResponseDto::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    //카테고리생성
    @Transactional
    public CategoryResponseDto createCategory(CategoryRequest request) {
        Category category = new Category(
                request.getCategory_name()
        );
        categoryRepository.save(category);

        return CategoryResponseDto.from(category);
    }

    //카테고리수정
    @Transactional
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequest request) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(() -> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setCategory_name(request.getCategory_name());
        category.setItems(itemRepository.findAllById(request.getItemIds()));

        return CategoryResponseDto.from(categoryRepository.save(category));
    }

    //카테고리 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new GeneralException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(categoryId);
    }
}

//CategoryService.java
//Order API 패턴을 최대한 따라해봤습니다.
//CategoryController.java 코드와 매칭을 시켰으며, 명령어(?)에 따른 로직 코드를 구현했습니다.
