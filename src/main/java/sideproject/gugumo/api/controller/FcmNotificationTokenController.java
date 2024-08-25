package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.request.FcmTokenDto;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.FcmNotificationTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "FcmNotificationToken")
public class FcmNotificationTokenController {

    private final FcmNotificationTokenService fcmNotificationTokenService;

    @PostMapping("/subscribe")
    @Operation(summary = "FCM 토큰 저장 및 갱신", description = "FCM 토큰을 저장 및 갱신합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 저장 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"북마크 생성 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                    @ExampleObject(name = "비로그인 사용자",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"토큰 저장 실패: 비로그인 사용자입니다.\"}"
                                            ),
                                    @ExampleObject(name = "권한 없음",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"토큰 저장 실패: 권한이 없습니다.\"}"
                                            )
                                    }))
            })
    public ApiResponse<String> subscribe(@AuthenticationPrincipal CustomUserDetails principal,
                                         @Valid @RequestBody FcmTokenDto fcmTokenDto) {

        fcmNotificationTokenService.subscribe(principal, fcmTokenDto);

        return ApiResponse.createSuccess("토큰 저장 및 갱신 완료");
    }
}
