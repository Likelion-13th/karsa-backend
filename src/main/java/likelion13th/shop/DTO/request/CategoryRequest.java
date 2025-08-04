package likelion13th.shop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {
    private String category_name;
    private List<Long> itemIds;
}

//CategoryRequest.java
//Order API 패턴을 최대한 따라하려고 했습니다.
//저의 ERD와 Category.java를 매칭시켰습니다.
