package likelion13th.shop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.shop.DTO.request.ItemRequest;
import likelion13th.shop.DTO.response.ItemResponseDto;
import likelion13th.shop.global.api.ApiResponse;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.api.SuccessCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*; //annotaion의 전부 다(all)

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    //모든 상품 조회
    @GetMapping
    @Operation(summary = "모든 상품 조회", description = "모든 상품을 조회하는 기능")
    public ApiResponse<?> findAllItems(){
        List<ItemResponseDto> items = itemService.getAllItems();

        if(items.isEmpty()){
            return ApiResponse.onSuccess(SuccessCode.ITEM_LIST_EMPTY, Collections.emptyList());
        }
        return ApiResponse.onSuccess(SuccessCode.ITEM_GET_SUCCESS, items);
    }

    //개별 상품 조회
    @GetMapping("/{itemId}")
    @Operation(summary = "개별 상품 조회", description = "개별 상품을 조회하는 기능")
    public ApiResponse<?> findItemById(@PathVariable Long itemId){
        log.info("[STEP 1] 개별 상품 조회 요청 수신...");

        try{
            ItemResponseDto item = itemService.getItemById(itemId);
            log.info("[STEP 2] 개별 주문 조회 성공");
            return ApiResponse.onSuccess(SuccessCode.ITEM_GET_SUCCESS, item);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 개별 상품 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //상품생성
    @PostMapping
    @Operation(summary = "상품 생성", description = "상품을 생성하는 기능")
    public ApiResponse<?> createItem(@RequestBody ItemRequest itemRequest){
        log.info("[STEP 1] 상품 생성 요청 수신...");
        try{
            ItemResponseDto item = itemService.createItem(itemRequest);
            log.info("[STEP 2] 상품 생성 성공");
            return ApiResponse.onSuccess(SuccessCode.ITEM_CREATE_SUCCESS,item);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 상품 생성 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //상품수정
    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품을 수정하는 기능")
    public ApiResponse<?> updateItem(@PathVariable Long itemId, @RequestBody ItemRequest itemRequest){
        log.info("[STEP 1] 상품 수정 요청 수신...");
        try{
            ItemResponseDto item = itemService.updateItem(itemId, itemRequest);
            log.info("[STEP 2] 상품 수정 성공");
            return ApiResponse.onSuccess(SuccessCode.ITEM_UPDATE_SUCCESS, item);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 상품 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    //상품삭제
    @DeleteMapping("/{itemId}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제하는 기능")
    public ApiResponse<?> deleteItem(@PathVariable Long itemId){
        log.info("[STEP 1] 상품 삭제 요청 수신...");
        try{
            itemService.deleteItem(itemId);
            log.info("[STEP 2] 상품 삭제 성공");
            return null;
        } catch (GeneralException e){
            log.error("❌ [ERROR] 상품 수정 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}

//ItemController.java
//Order API 패턴을 참고하였으며 과제에서 요구했던 명령어들인
//'모든 상품조회'.'개별상품 조회','상품 생성' 등에 맞는 코드를 짜 봤습니다.
//ItemService.java와 매칭을 시켰습니다.

