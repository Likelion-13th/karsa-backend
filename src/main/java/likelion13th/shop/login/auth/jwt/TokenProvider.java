package likelion13th.shop.login.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import likelion13th.shop.login.auth.dto.JwtDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component

public class TokenProvider {
    private final Key secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenProvider(
            @Value("${JWT_SECRET") String secretKey,
            @Value("${JWT_EXPIRATION") long accessTokenExpiration,
            @Value("${JWT_REFRESH_EXPIRATION") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public JwtDto generateTokens(UserDetails userDetails){
        log.info("JWT 생성: 사용자 {}", userDetails.getUsername());

        String userId = userDetails.getUsername();

        String authorities = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        String accessToken = createToken(userId, authorities, accessTokenExpiration);

        String refreshToken = createToken(userId,null, refreshTokenExpiration);

        log.info("JWT 생성 완료: 사용자 {}", userDetails.getUsername());
        return new JwtDto(accessToken, refreshToken);
    }

    private String createToken(String providerId, String authorities, long expirationTime) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(providerId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256);

        if (authorities != null) {
            builder.claim("authorities", authorities);
        }

        return builder.compact().toString();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        try{
            return  Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token).getBody();

        } catch (ExpiredJwtException e) {
            log.warn("토큰 만료");
            throw e;
        } catch (JwtException e) {
            log.warn("JWT 피싱 실패");
            throw new RuntimeException(e);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthFromClaims(Claims claims) {
        String authoritiesString = claims.get("authorities", String.class);
        if (authoritiesString != null || authoritiesString.isEmpty()) {
            log.warn("권한 정보가 없다 - 기본 ROLE_USER 부여");
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return Arrays.stream(authoritiesString.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public Claims parseClaimsAllowExpired(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

/*
1) 왜 필요한가?
- 유저에게 accesstoken과 refreshtoken을 제공하기 위해 필요한 파일이라고 생각한다.
- 우리가 카카오 개발(?) 페이지에서 발급받았던 여러 키들을 환경변수에 넣어놓았던 작업을 했던 것으로 기억하는데
Tokenprovider 함수를 통해 환경변수에 저장된 여러 키들을 손쉽게 가져올 수 있다.
- userId를 인자로 넘겨받으면 createToken 함수를 통해 토큰을 만들 수 있는것으로 보인다.

2) 없으면/틀리면?
- 이 파일이 없으면 유저에게 액세스/리프레시 토큰을 제공할 수 없을것으로 보인다.
그렇게 되면 유저가 로그인 할 수 없을거라고 생각하며 그만큼 중요한 파일이라고 생각된다.
 */
