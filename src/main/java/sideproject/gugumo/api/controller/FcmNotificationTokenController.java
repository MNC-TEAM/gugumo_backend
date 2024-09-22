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
import sideproject.gugumo.api.swaggerapi.FcmNotificationTokenApi;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.request.FcmTokenDto;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.FcmNotificationTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "FcmNotificationToken")
public class FcmNotificationTokenController implements FcmNotificationTokenApi {

    private final FcmNotificationTokenService fcmNotificationTokenService;

    @PostMapping("/subscribe")
    public ApiResponse<String> subscribe(@AuthenticationPrincipal CustomUserDetails principal,
                                         @Valid @RequestBody FcmTokenDto fcmTokenDto) {

        fcmNotificationTokenService.subscribe(principal, fcmTokenDto);

        return ApiResponse.createSuccess("토큰 저장 및 갱신 완료");
    }
}
