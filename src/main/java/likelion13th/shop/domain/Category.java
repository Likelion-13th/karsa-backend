package likelion13th.shop.domain;



import jakarta.persistence.*;
import likelion13th.shop.domain.entity.BaseEntity;
import likelion13th.shop.global.constant.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "orders") //예약어 회피
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

}
