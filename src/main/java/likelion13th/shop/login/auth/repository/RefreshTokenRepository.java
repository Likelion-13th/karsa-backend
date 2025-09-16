package likelion13th.shop.login.auth.repository;

import likelion13th.shop.domain.User;
import likelion13th.shop.login.auth.jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteByUser(@Param("user") User user);
}

/*
1) 왜 필요한가?
- Optional<RefreshToken> findByUser(User user) User에게 리프레시 토큰을 제공(?)해주기 위해
필요한것이라 생각한다.

2) 없으면/틀리면?
- refreshtoken을 가질 수 없게 되고 유저는 accesstoken만을 써야하는데 accesstoken은 유효 기간이 짧아
accesstoken이 만료되면 유저가 다시 로그인을 해야하는 등 번거로운 과정을 유저가 겪어야한다.
 */
