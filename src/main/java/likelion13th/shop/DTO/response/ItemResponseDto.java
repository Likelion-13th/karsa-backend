package likelion13th.shop.DTO.response;

import likelion13th.shop.domain.Category;
import likelion13th.shop.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String item_name;
    private int price;
    private String imagePath;
    private String brand;
    private List<Category> categories;
    public static ItemResponseDto from(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getItem_name(),
                item.getPrice(),
                item.getImagePath(),
                item.getBrand(),
                item.getCategories()
        );
    }
}

//ItemResponseDto.java
//Order API의 패턴을 참고했습니다.
//데이터 배송 상자로서 클라이언트가 필요로하는 정보만 골라서 이곳에 담아 안전하게 전달합니다.



