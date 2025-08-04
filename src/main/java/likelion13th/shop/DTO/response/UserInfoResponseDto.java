package likelion13th.shop.DTO.response;

import likelion13th.shop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private Long id;
    private String username;
    private String password;
    private Long mileage;
    private String Address;
    private String field;
    private Long postnumber;
    private Long recentlyused;
    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getMileage(),
                user.getAddress(),
                user.getField(),
                user.getPostnumber(),
                user.getRecentlyused()
        );
    }
}
//UserInfoResponseDto.java
//Order API의 패턴을 참고했습니다.
//데이터 배송 상자로서 클라이언트가 필요로하는 정보만 골라서 이곳에 담아 안전하게 전달합니다.
