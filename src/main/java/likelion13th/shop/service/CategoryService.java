package likelion13th.shop.service;

import jakarta.transaction.Transactional;
import likelion13th.shop.DTO.response.ItemResponseDto;
import likelion13th.shop.DTO.request.CategoryRequest;
import likelion13th.shop.DTO.response.CategoryResponseDto;
import likelion13th.shop.domain.Category;
import likelion13th.shop.domain.User;
import likelion13th.shop.domain.Item;
import likelion13th.shop.domain.Order;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.repository.CategoryRepository;
import likelion13th.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    /** 카테고리 존재 여부 확인 **/
    // 이런 식으로 검증하는 메서드를 따로 만들어서 재사용성 높일 수 있음
    public Category findCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(()-> new GeneralException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    /** 카테고리 별 상품 목록 조회 **/
    // DTO에 담아서 반환
    public List<ItemResponseDto> getItemsByCategory(Long categoryId) {
        // 카테고리 유효성 검사
        Category category = findCategoryById(categoryId);

        List<Item> items = category.getItems();
        return items.stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

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
