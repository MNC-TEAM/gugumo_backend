package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.api.swaggerapi.MemberApi;
import sideproject.gugumo.domain.dto.memberDto.*;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.MailSenderService;
import sideproject.gugumo.service.MemberService;
import sideproject.gugumo.swagger.MemberInfoDtoResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Member")
public class MemberApiController implements MemberApi {

    private final MemberService memberService;
    private final MailSenderService mailService;

    @PostMapping("/api/v1/emailLogin")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "로그인", description = "이메일로 로그인합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"success\",\n" +
                                                    "    \"data\": \"Bearer ~!@~!@!#E@#$E@#%$#%EFSDFDsdfiojwqeoifhsdlkaf\"\n" +
                                                    "    \"message\": null\n" +
                                                    "}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "로그인 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"fail\",\n" +
                                                    "    \"data\": null,\n" +
                                                    "    \"message\": \"아이디 혹은 비밀번호가 틀렸습니다.\"\n" +
                                                    "}"
                                    )))
            }
    )
    public ApiResponse<String> emailLogin(@RequestBody EmailLoginRequestDto emailLoginRequestDto) {

        String token = memberService.emailLogin(emailLoginRequestDto);

        return ApiResponse.createSuccess("Bearer " + token);
    }

    @PostMapping("/api/v2/member")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원 가입", description = "이메일 인증이 완료된 계정의 회원가입을 진행합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 회원가입 완료",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status\": \"success\",\n" +
                                            "    \"data\": true,\n" +
                                            "    \"message\": null\n" +
                                            "}"
                            ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력이 올바르지 못함",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                    examples = {
                            @ExampleObject(name = "올바르지 못한 입력",
                                    value = """
                                            {
                                                "status": "fail",
                                                "data": null,
                                                "message": "입력이 올바르지 못합니다."
                                            }
                                                                                        
                                            """
                            ),
                            @ExampleObject(name = "이메일 인증 에러",
                            value = """
                                    {
                                          "status": "fail",
                                          "data": null,
                                          "message": "이메일 인증 에러"
                                    }
                                      
                                                                                        
                                    """
                            ),
                            @ExampleObject(name = "닉네임 중복",
                            value = """
                                    {
                                           "status": "fail",
                                           "data": null,
                                           "message": "이미 존재하는 닉네임입니다."
                                     }
                                                                                        
                                    """
                            ),
                    })),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "이미 존재하는 회원",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
            examples = @ExampleObject(
                    value = """
                            {
                                "status": "fail",
                                "data": null,
                                "message": "이미 존재하는 회원입니다."
                            }
                            """
            )))
    })
    public ApiResponse<Boolean> joinMemberWithEmailAuth(@RequestBody @Valid SignUpEmailMemberDto signUpEmailMemberDto) {

        mailService.checkAuthNum(signUpEmailMemberDto.getUsername(), signUpEmailMemberDto.getEmailAuthNum());

        Long joinId = memberService.joinMember(signUpEmailMemberDto);

        return ApiResponse.createSuccess(true);
    }

    @GetMapping("/api/v1/member")
    @Operation(summary = "회원 정보", description = "회원 정보를 조회합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 정보",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberInfoDtoResponse.class),
            examples = @ExampleObject(value = """
                    {
                        "status": "success",
                        "data": {
                            "username": "donald010@naver.com",
                            "nickname": "nickname",
                            "favoriteSports": "BADMINTON,FUTSAL,BASEBALL"
                        },
                        "message": null
                    }
                    """)))
    })
    public ApiResponse<MemberInfoDto> getMemberInfo(@AuthenticationPrincipal CustomUserDetails principal) {

        long id = principal.getId();

        MemberInfoDto memberInfoDto = memberService.getMemberInfo(id);

        return ApiResponse.createSuccess(memberInfoDto);
    }

    @PatchMapping("/api/v1/member/updateNickname")
    @Operation(summary = "닉네임 변경", description = "닉네임을 변경합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 변경 완료",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status\": \"success\",\n" +
                                            "    \"data\": \"changeNickna123me\",\n" +
                                            "    \"message\": null\n" +
                                            "}"
                            ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "닉네임 중복으로 인한 변경 실패",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status\": \"fail\",\n" +
                                            "    \"data\": null,\n" +
                                            "    \"message\": \"이미 존재하는 닉네임입니다.\"\n" +
                                            "}"
                            )))
    })
    public ApiResponse<MemberInfoDto> updateMemberNickname(@AuthenticationPrincipal CustomUserDetails principal,
                                                   @RequestBody UpdateMemberNicknameDto updateMemberNicknameDto) {

        String updateNickname = updateMemberNicknameDto.getNickname();
        long id = principal.getId();

        // 회원 정보 수정
        memberService.updateNickname(id, updateNickname);

        // 수정한 정보로 회원 조회
        MemberInfoDto memberInfo = memberService.getMemberInfo(id);

        return ApiResponse.createSuccess(memberInfo);
    }

    @GetMapping("/api/v1/member/checkDuplicateNickname")
    @Operation(summary = "닉네임 중복 확인", description = "닉네임의 중복 여부를 확인합니다.",
    responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "닉네임 중복 여부",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = "{\n" +
                                            "    \"status\": \"success\",\n" +
                                            "    \"data\": false,\n" +
                                            "    \"message\": null\n" +
                                            "}"
                            )))
    })
    public ApiResponse<Boolean> checkDuplicateNickname(@RequestParam @Parameter(description = "중복을 확인할 닉네임") String nickname) {

        Boolean isExistNickname = memberService.isExistNickname(nickname);

        return ApiResponse.createSuccess(isExistNickname);
    }

    @PatchMapping("/api/v1/member/updatePassword")
    @Operation(summary = "비밀번호 변경", description = "비밀번호를 변경합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "비밀번호 변경 여부",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"success\",\n" +
                                                    "    \"data\": true,\n" +
                                                    "    \"message\": null\n" +
                                                    "}"
                                    )))
            })
    public ApiResponse<Boolean> updateMemberPassword(@AuthenticationPrincipal CustomUserDetails principal,
                                                    @RequestBody UpdateMemberPasswordDto updateMemberPasswordDto) {

        long id = principal.getId();

        memberService.updatePassword(id, updateMemberPasswordDto.getPassword());

        return ApiResponse.createSuccess(true);
    }

    @DeleteMapping("/api/v1/member")
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 탈퇴 여부",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"success\",\n" +
                                                    "    \"data\": true,\n" +
                                                    "    \"message\": null\n" +
                                                    "}"
                                    )))
            })
    public ApiResponse<Boolean> deleteMember(@AuthenticationPrincipal CustomUserDetails principal) {

        long id = principal.getId();

        memberService.deleteMember(id);

        return ApiResponse.createSuccess(true);
    }

    @PostMapping("/api/v1/resetPassword")
    @Operation(summary = "비밀번호 초기화", description = "비밀번호를 초기화합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "임시 비밀번호 전송 여부",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"success\",\n" +
                                                    "    \"data\": true,\n" +
                                                    "    \"message\": null\n" +
                                                    "}"
                                    )))
            }
    )
    public ApiResponse<Boolean> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {

        String username = resetPasswordDto.getEmail();

        String newPassword = memberService.resetPassword(username);

        mailService.resetPassword(username, newPassword);

        return ApiResponse.createSuccess(true);
    }
}
