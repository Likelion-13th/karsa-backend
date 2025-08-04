package likelion13th.shop.repository;

import likelion13th.shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    // user_id 기반 사용자 찾기 (feature/4)
    // 내 정보 조회
    Optional<User> findById(Long userId);

    boolean existsById(Long userId);

    // providerId(카카오 고유 ID) 기반 조회 (feature/4)
    Optional<User> findByProviderId(String providerId);

    boolean existsByProviderId(String providerId);

    // usernickname(닉네임) 기반 사용자 찾기 (develop)
    List<User> findByUsernickname(String usernickname);

    // 향후 필요 시 사용할 수 있도록 주석 유지
    //Optional<User> findByKakaoId(String kakaoId);

    //user_id 기반 마일리지 조회
    Optional<User> findMileageById(Long userId);

    //user_id 기반 주소 조회
    Optional<User> findAddressById(Long userId);

}
//UserRepository.java
//Order API 패턴을 최대한 따라하려고 했습니다.
//JpaRepository를 상속받아 기본 CRUD 메서드를 자동 제공받을 수 있었습니다.

