package likelion13th.shop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    private String item_name;
    private int item_price;
    private int item_quantity;
    private String item_brand;
    private String imagePath;
    private boolean isNewItem;
    private List<Long> categoryIds;

}

//ItemRequest.java
//Order API 패턴을 최대한 따라하려고 했습니다.
//저의 ERD와 Item.java를 매칭시켰습니다.
