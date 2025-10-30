package likelion13th.shop.service;

import jakarta.transaction.Transactional;
import likelion13th.shop.DTO.request.UserInfoRequest;
import likelion13th.shop.DTO.response.UserInfoResponseDto;
import likelion13th.shop.domain.User;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.repository.CategoryRepository;
import likelion13th.shop.repository.ItemRepository;
import likelion13th.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;

    //내 정보 조회
    @Transactional
    public UserInfoResponseDto getUserInfoById(Long userId) {
        return userRepository.findById(userId).map(UserInfoResponseDto::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

    //내 마일리지 조회
    @Transactional
    public UserInfoResponseDto getUserMileageById(Long userId) {
        return userRepository.findById(userId).map(UserInfoResponseDto::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }

    //내 주소 조회
    @Transactional
    public UserInfoResponseDto getUserAddressById(Long userId) {
        return  userRepository.findById(userId).map(UserInfoResponseDto::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.USER_NOT_FOUND));
    }
}
//UserInfoService.java
//Order API 패턴을 최대한 따라해봤습니다.
//UserInfoController.java 코드와 매칭을 시켰으며, 명령어(?)에 따른 로직 코드를 구현했습니다.
