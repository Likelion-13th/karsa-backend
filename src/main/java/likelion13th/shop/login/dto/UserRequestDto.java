package likelion13th.shop.login.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserRequestDto {
    @Schema(description = "UserReqDto")
    @Getter
    @Builder
    @AllArgsConstructor
    //카카오한테 받아오는 userId, providerId 등의 통(담을 용기) 만들어줌
    public static class UserReqDto {
        private Long userId;
        private String providerId;
        private String usernickname;

    }
}
