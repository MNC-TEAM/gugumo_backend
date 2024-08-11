package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.domain.dto.memberDto.KakaoLoginRequestDto;
import sideproject.gugumo.domain.dto.memberDto.KakaoUserInfoResponseDto;
import sideproject.gugumo.domain.dto.memberDto.SignUpKakaoMemberDto;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.KakaoService;
import sideproject.gugumo.service.MemberService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Kakao")
public class KakaoController {

    private final KakaoService kakaoService;
    private final MemberService memberService;

    /**
     * deprecated
     * @param
     * @return
     */
//    @GetMapping("/kakao/login")
//    public ApiResponse<String> login(@RequestParam(name = "code") String code) {
//
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
//
//        Boolean isJoined = memberService.isJoinedKakaoMember(userInfo.getId());
////        Boolean isJoined = memberService.isExistUsername(userInfo.get)
//        StringBuilder loginResult = new StringBuilder();
//
//        if(isJoined) {
//            loginResult.append("Bearer ").append(memberService.kakaoLogin(userInfo.getKakaoAccount().getProfile().getNickName()));
//        }
//        else {
//            loginResult.append("not joined");
//        }
//
////        return loginResult.toString();
//        return ApiResponse.createSuccess(loginResult.toString());
//    }


    @PostMapping("kakao/login")
    @Operation(summary = "카카오 로그인", description = "카카오 계정으로 로그인합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "해당 사용자에 대한 access token",
            content = @Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponse.class),
            examples = @ExampleObject("{\n" +
                    "    \"status\": \"success\",\n" +
                    "    \"data\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ1c2VybmFtZSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MTkyNDExMzEsImV4cCI6MTcxOTMyNzUzMX0.QRiTv8Z-Bv5knTMLLxTvmpo6Sz_0k7TfzRtSpEmUSvw\",\n" +
                    "    \"message\": null\n" +
                    "}"))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "회원이 없음",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"회원이 없습니다.\"}"
                            )))
    })
    public ApiResponse<String> login(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto) {

        String token = memberService.kakaoLogin(kakaoLoginRequestDto.getUsername());

        return ApiResponse.createSuccess("Bearer " + token);
    }

    @PostMapping("/api/v1/kakao/member")
    @Operation(summary = "카카오 회원가입", description = "카카오 계정으로 회원가입을 진행합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 회원가입 완료",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status\": \"success\",\n" +
                                            "    \"data\": true,\n" +
                                            "    \"message\": null\n" +
                                            "}"
                            )))
    })
    public ApiResponse<Boolean> join(@RequestBody SignUpKakaoMemberDto signUpKakaoMemberDto) {

        memberService.kakaoJoinMember(signUpKakaoMemberDto);

        return ApiResponse.createSuccess(true);
    }
}
