package likelion13th.shop.DTO.response;

import likelion13th.shop.domain.Category;
import likelion13th.shop.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String category_name;
    private List<Item> items;
    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getCategory_name(),
                category.getItems()
        );
    }
}
//CategoryResponseDto.java
//Order API의 패턴을 참고했습니다.
//데이터 배송 상자로서 클라이언트가 필요로하는 정보만 골라서 이곳에 담아 안전하게 전달합니다.
