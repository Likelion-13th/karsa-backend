package likelion13th.shop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion13th.shop.DTO.request.CategoryRequest;
import likelion13th.shop.DTO.response.CategoryResponseDto;
import likelion13th.shop.DTO.response.ItemResponseDto;
import likelion13th.shop.global.api.ApiResponse;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.api.SuccessCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*; //annotaion의 전부 다(all)

import java.util.Collections;
import java.util.List;

@Tag(name = "카테고리", description = "카테고리 관련 API 입니다.")
@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    //모든 카테고리 조회
    @GetMapping
    @Operation(summary = "모든 카테고리 조회", description = "모든 카테고리를 조회하는 기능")
    public ApiResponse<?> findAllCategories(){
        List<CategoryResponseDto> categories = categoryService.getAllCategories();

        if(categories.isEmpty()){
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_EMPTY, Collections.emptyList());
        }
        return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_GET_SUCCESS, categories);
    }

    //개별 카테고리 조회
    @GetMapping("/{categoryId}")
    @Operation(summary = "개별 카테고리 조회", description = "개별 카테고리를 조회하는 기능")
    public ApiResponse<?> findCategoryById(@PathVariable Long categoryId){
        log.info("[STEP 1] 개별 카테고리 조회 요청 수신...");

        try{
            CategoryResponseDto category = categoryService.getCategoryById(categoryId);
            log.info("[STEP 2] 개별 카테고리 조회 성공...");
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_GET_SUCCESS, category);
        } catch (GeneralException e) {
            log.error("❌ [ERROR] 개별 상품 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e) {
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리 생성
    @PostMapping
    @Operation(summary = "카테고리 생성", description = "카테고리를 생성하는 기능")
    public ApiResponse<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        log.info("[STEP 1] 카테고리 생성 요청 수신...");
        try{
            CategoryResponseDto item = categoryService.createCategory(categoryRequest);
            log.info("[STEP 2] 카테고리 생성 성공");
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_CREATE_SUCCESS,category);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 카테고리 생성 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리수정
    @PutMapping("/{CategoryId}")
    @Operation(summary = "카테고리 수정", description = "카테고리를 수정하는 기능")
    public ApiResponse<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest){
        log.info("[STEP 1] 카테고리 수정 요청 수신...");
        try{
            CategoryResponseDto category = categoryService.updateCategory(categoryId, categoryRequest);
            log.info("[STEP 2] 카테고리 수정 성공");
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_UPDATE_SUCCESS, category);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 카테고리 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //카테고리삭제
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제하는 기능")
    public ApiResponse<?> deleteCategory(@PathVariable Long categoryId){
        log.info("[STEP 1]  카테고리 삭제 요청 수신...");
        try{
            categoryService.deleteCategory(categoryId);
            log.info("[STEP 2] 카테고리 삭제 성공");
            return null;
        } catch (GeneralException e){
            log.error("❌ [ERROR] 상품 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 상품 조회(카테고리별)
    // 컨트롤러에서 Optional 처리하고 있음
    // 컨트롤러에서는 예외처리만 하고자 함!
    /** 카테고리 별 상품 조회**/
    @GetMapping("/{categoryId}/items")
    @Operation(summary = "카테고리별 상품 조회", description = "상품을 카테고리 별로 조회합니다.")
    public ApiResponse<?> getItemsByCategory(@PathVariable Long categoryId) {
        List<ItemResponse> items = categoryService.getItemsByCategory(categoryId);

        //상품 없을 시 : 성공 응답 + 빈 리스트 반환
        if (items.isEmpty()) {
            return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_EMPTY, Collections.emptyList());
        }

        return ApiResponse.onSuccess(SuccessCode.CATEGORY_ITEMS_GET_SUCCESS, items);

    }
}

//CategoryController.java
//Order API 패턴을 참고하였으며 과제에서 요구했던 명령어들인
//'모든 카테고리조회'.'개별 카테고리 조회','카테고리 생성' 등에 맞는 코드를 짜 봤습니다.
//CategoryService.java와 매칭을 시켰습니다.
