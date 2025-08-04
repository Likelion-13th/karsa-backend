package likelion13th.shop.domain;


import jakarta.persistence.*;
import likelion13th.shop.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "category") //예약어 회피
@NoArgsConstructor
//파라미터가 없는 디폴트 생성자 자동으로 생성

public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //유일하게 생성... 중복 안되게
    @Column(name = "category_id")//그냥 이름
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @Column(nullable = false)
    private String category_name;

    //item과 다대다 연관관계 설정
    @ManyToMany
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    public Category(String category_name) {
        this.category_name = category_name;
    }

}

//Category.java
//Order.java와 제가 만든 ERD 테이블을 참고해 코드를 짜 봤습니다.
//ERD와 Category.java 매칭을 시켰습니다.
