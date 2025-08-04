package likelion13th.shop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UserInfoRequest {
    private String username;
    private String password;
    private Long mileage;
    private String address;
    private String field;
    private Long postnumber;
    private Long recentlyused;
}
//UserInfoRequest.java
//Order API 패턴을 최대한 따라하려고 했습니다.
//저의 ERD와 User.java를 매칭시켰습니다.

