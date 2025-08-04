package likelion13th.shop.Controller;

import io.swagger.v3.oas.annotations.Operation;
import likelion13th.shop.DTO.request.UserInfoRequest;
import likelion13th.shop.DTO.response.UserInfoResponseDto;
import likelion13th.shop.global.api.ApiResponse;
import likelion13th.shop.global.api.ErrorCode;
import likelion13th.shop.global.api.SuccessCode;
import likelion13th.shop.global.exception.GeneralException;
import likelion13th.shop.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*; //annotaion의 전부 다(all)

import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInfoController {
    private  final  UserInfoService userInfoService;

    //내 정보 조회
    @GetMapping
    @Operation(summary = "내 정보 조회", description = "내 정보를 조회하는 기능")
    public ApiResponse<?> findUserInfoById(@PathVariable Long userId) {
        log.info("[STEP 1] 내 정보 조회 요청 수신...");

        try{
            UserInfoResponseDto userInfo=userInfoService.getUserInfoById(userId);
            log.info("[STEP 2] 내 정보 조회 성공...");
            return ApiResponse.onSuccess(SuccessCode.USER_INFO_GET_SUCCESS,userInfo);
        } catch (GeneralException e) {
            log.error("❌ [ERROR] 내 정보 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    //내 마일리지 조회
    @GetMapping
    @Operation(summary = "내 마일리지 조회", description = "내 마일리지를 조회하는 기능")
    public ApiResponse<?> findUserMileageById(@PathVariable Long userId) {
        log.info("[STEP 1] 내 마일리지 조회 요청 수신...");

        try{
            UserInfoResponseDto userMileage=userInfoService.getUserMileageById(userId);
            log.info("[STEP 2] 내 마일리지 조회 성공...");
            return ApiResponse.onSuccess(SuccessCode.USER_MILEAGE_GET_SUCCESS,userMileage);
        } catch (GeneralException e) {
            log.error("❌ [ERROR] 내 정보 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    //내 주소 조회
    @GetMapping
    @Operation(summary = "내 주소 조회", description = "내 주소를 조회하는 기능")
    public ApiResponse<?> findUserAddressById(@PathVariable Long userId) {
        log.info("[STEP 1] 내 주소 조회 요청 수신...");

        try{
            UserInfoResponseDto userAddress=userInfoService.getUserAddressById(userId);
            log.info("[STEP 2] 내 주소 조회 성공...");
            return ApiResponse.onSuccess(SuccessCode.USER_ADDRESS_GET_SUCCESS,userAddress);
        } catch (GeneralException e){
            log.error("❌ [ERROR] 내 주소 조회 중 예외 발생: {}", e.getReason().getMessage());
            throw e;
        } catch (Exception e){
            log.error("❌ [ERROR] 알 수 없는 예외 발생: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
//UserInfoController.java
//Order API 패턴을 참고하였으며 과제에서 요구했던 명령어들인
//'나의 정보 조회'.'나의 마일리지 조회','나의 주소 조회' 등에 맞는 코드를 짜 봤습니다.
//UserInfoService.java와 매칭을 시켰습니다.
