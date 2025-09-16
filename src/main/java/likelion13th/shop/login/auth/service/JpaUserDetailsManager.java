package likelion13th.shop.login.auth.service;

import likelion13th.shop.domain.User;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.login.auth.jwt.CustomUserDetails;
import likelion13th.shop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaUserDetailsManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String providerId) throws UsernameNotFoundException{
        User user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> {
                    log.warn("사용자 정보 없음!");
                    throw new GeneralException(ErrorCode.USER_NOT_FOUND);
                });
        return CustomUserDetails.fromEntity(user);
    }

    @Override
    public void createUser(UserDetails user) {
        if(userExists(user.getUsername())){
            throw new GeneralException(ErrorCode.ALREADY_USED_NICKNAME);
        }

        try{
            User newUser = ((CustomUserDetails) user).toEntity();

            userRepository.save(newUser);
            log.info("사용자 생성 완료");
        } catch (ClassCastException e){
            log.error("UserDetails -> CustomUserDetails로 변환 실패!");
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean userExists(String providerId) {
        return userRepository.existsByProviderId(providerId);
    }

    /**
     * // 사용자 정보 업데이트 (현재 미구현)
     * // - 소셜 로그인 시 서버에서 직접 갱신할 데이터 범위가 명확해진 뒤 구현 권장
     */
    @Override
    public void updateUser(UserDetails user) {
        log.error("사용자 정보 업데이트는 지원되지 않음 (provider_id): {}", user.getUsername());
        throw new UnsupportedOperationException("사용자 업데이트 기능은 아직 지원되지 않습니다.");
    }

    /**
     * // 사용자 삭제 (현재 미구현)
     * // - 실제 삭제 대신 '탈퇴 플래그'로 관리하는 소프트 삭제 전략을 권장
     */
    @Override
    public void deleteUser(String providerId) {
        log.error("사용자 삭제는 지원되지 않음 (provider_id): {}", providerId);
        throw new UnsupportedOperationException("사용자 삭제 기능은 아직 지원되지 않습니다.");
    }

    /**
     * // 비밀번호 변경 (소셜 로그인은 비밀번호를 사용하지 않음)
     * // - 자체 회원 가입/로그인 기능을 추가할 때 구현
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        log.error("비밀번호 변경은 지원되지 않음.");
        throw new UnsupportedOperationException("비밀번호 변경 기능은 아직 지원되지 않습니다.");
    }
}

/*
1) 왜 필요한가?
- userRepository.findByProviderId(providerId) userRepository에 있는 providerId를 통해
유저가 있는 사람인지 없는 사람인지 판단 후(로딩) 유저를 생성하기 위해 필요하다.

2) 없으면/틀리면?
- 유저가 우리 서비스에 존재하는(이미 가입되어있는) 사람인지 아닌지 판단할 수 없게 되며 로그인 과정부터
(첫 시작부터) 꼬이게 된다.
- 잘못된 유저 정보를 불러올 수 있게 되며 로직이 꼬이게 된다.
 */