package likelion13th.shop.login.auth.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import likelion13th.shop.domain.Address;
import likelion13th.shop.domain.User;
import likelion13th.shop.login.auth.dto.JwtDto;
import likelion13th.shop.login.auth.jwt.CustomUserDetails;
import likelion13th.shop.login.auth.service.JpaUserDetailsManager;
import likelion13th.shop.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException{
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        String providerId = (String) oAuth2User.getAttributes().get("provider_Id");
        String nickname = (String) oAuth2User.getAttributes().get("nickname");

        if (!jpaUserDetailsManager.userExists(providerId)) {
            User newUser = User.builder()
                    .providerId(providerId)
                    .usernickname(nickname)
                    .deletable(true)
                    .build();

            newUser.setAddress(new Address("10540", "경기도 고양시 덕양구 항공대학로 76", "한국항공대학교"));

            CustomUserDetails userDetails = new CustomUserDetails(newUser);
            jpaUserDetailsManager.createUser(userDetails);
            log.info("신규 회원 등록이용");
        } else{
            log.info("기존 회원이용");
        }

        JwtDto jwt=userService.jwtMakeSave(providerId);

        String frontendRedirectUri = request.getParameter("redirect_uri");
        List<String> authorizeUris = List.of(
                "각자 여러분 배포 URL 넣어주세요",
                "http://localhost:3000"
        );
        if(frontendRedirectUri!=null || authorizeUris.contains(frontendRedirectUri)){
            frontendRedirectUri = "프론트 배포 URL 여기도 똑같이 넣어주세요~";
        }

        String redirectUrl = UriComponentsBuilder.fromUriString(frontendRedirectUri).build().toUriString();

        log.info("리다이렉트 시켜보아요: {}", frontendRedirectUri);
        response.sendRedirect(redirectUrl);
    }

}

/*
1) 왜 필요한가?
- 유저가 새로 가입한 신규 유저인지 아니면 기존 원래 있던 유저인지 확인하기 위해 필요하다
- 만약 신규 유저면 userService.jwtMakeSave를 호출해 새롭게 만들어주어야한다.
- redirectUrl을 통해 프론트가 배포한 주소로 리다이렉트하게 도와준다.

2) 없으면/틀리면?
- 로그인한 유저가 신규 유저인지 기존유저인지 확인할 수 없어 혼란을 야기할 수 있으며,
신규 유저의 유입을 막게 된다.(?!)
- 잘못된 주소로 리다이렉트하게 되며 로직이 꼬이게 된다.
 */
