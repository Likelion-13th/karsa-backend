package likelion13th.shop.login.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter

public class JwtDto {
    private String accessToken;
    private String refreshToken;

    public JwtDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

/*
1) 왜 필요한가?
- 문자열로 된 액세스 토큰과, 리프레시 토큰을 JwtDto 함수가 인자로 받아 토큰틀을 만들어(?) 값을 반환(?) 해주기위해
필요한 파일로 생각된다.
- 리프래시 토큰을 저장하는 refreshtokenrepository와 refreshtoken, tokenprovider 엔티티들과 관계를
맺고 있는 것으로 생각되는데 JwtDto 파일이 가장 상위 파일(?) 기본이 되는 파일(?)이라고 생각된다.

2) 없으면/틀리면?
- 앞서 말했듯 refreshtokenrepository와 refreshtoken, tokenprovider 파일들과 관계를 맺고 있기 때문에
이 파일이 없거나 잘못되면 나머지 토큰 파일들의 로직이 꼬리물기처럼 연달아 깨질 것으로 생각된다.

 */