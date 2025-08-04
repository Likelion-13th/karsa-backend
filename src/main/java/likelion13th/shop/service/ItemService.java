package likelion13th.shop.service;

import jakarta.transaction.Transactional;
import likelion13th.shop.DTO.request.ItemRequest;
import likelion13th.shop.DTO.request.OrderCreateRequest;
import likelion13th.shop.DTO.response.ItemResponseDto;
import likelion13th.shop.DTO.response.OrderResponseDto;
import likelion13th.shop.domain.Item;
import likelion13th.shop.domain.User;
import likelion13th.shop.domain.Order;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.constant.OrderStatus;
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
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    //모든상품조회
    @Transactional
    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponseDto::from)
                .collect(Collectors.toList());
    }

    //개별상품조회
    @Transactional
    public ItemResponseDto getItemById(Long itemId) {
        return itemRepository.findById(itemId).map(ItemResponseDto::from)
                .orElseThrow(()->new GeneralException(ErrorCode.ITEM_NOT_FOUND));
    }

    //상품생성
    @Transactional
    public ItemResponseDto createItem(ItemRequest request) {
        Item item=new Item(
                request.getItem_name(),
                request.getItem_price(),
                request.getImagePath(),
                request.getItem_brand(),
                request.isNewItem()
        );
        item.setCategories(categoryRepository.findAllById(request.getCategoryIds()));
        itemRepository.save(item);

        return ItemResponseDto.from(item);

    }

    //상품수정
    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemRequest request) {
        Item item=itemRepository.findById(itemId)
                .orElseThrow(()->new GeneralException(ErrorCode.ITEM_NOT_FOUND));
        item.setItem_name(request.getItem_name());
        item.setPrice(request.getItem_price());
        item.setImagePath(request.getImagePath());
        item.setBrand(request.getItem_brand());
        item.setNew(request.isNewItem());
        item.setCategories(categoryRepository.findAllById(request.getCategoryIds()));

        return ItemResponseDto.from(itemRepository.save(item));
    }

    //상품 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        if(!itemRepository.existsById(itemId)) {
            throw new GeneralException(ErrorCode.ITEM_NOT_FOUND);
        }
        itemRepository.deleteById(itemId);
    }
}

//ItemService.java
//Order API 패턴을 최대한 따라해봤습니다.
//ItemController.java 코드와 매칭을 시켰으며, 명령어(?)에 따른 로직을 구핸시켜놨습니다.

