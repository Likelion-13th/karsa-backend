package likelion13th.shop.login.auth.utils;

import likelion13th.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2User oAth2User = super.loadUser(userRequest);

        String providerId = oAth2User.getAttributes().get("id").toString();

        @SuppressWarnings("unchecked")
        Map<String, Object> properties =
                (Map<String, Object>) oAth2User.getAttributes().getOrDefault("properties", Collections.emptyMap());
        String nickname = properties.getOrDefault("nickname", "카카오사용자").toString();

        Map<String, Object> extendedAttributes = new HashMap<>(oAth2User.getAttributes());
        extendedAttributes.put("provider_id", providerId);
        extendedAttributes.put("nickname", nickname);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                extendedAttributes,
                "provider_id"
        );
    }
}

/*
1) 왜 필요한가
- 카카오에서 받아온 attributeKey를 우리가 만든 서비스의 표준 키로 정규화하기 위해 필요하다.
(id는 provider_id로, properties.nickname을 nickname으로)
- Spring Security가 principal의 name으로 사용할 키를 명확히 지정해
  이후 핸들러/서비스가 동일 식별자(provider_id)로 동작하게 한다.

2) 없으면/틀리면?
- getName()이 우리가 기대하는 provider_id가 아니어서 신규 가입/토큰 발급 등 후속 로직이 깨진다.
- 닉네임 키가 일관되지 않아 화면/로그/저장 로직에서 속성명이 뒤섞일 수 있다.
 */