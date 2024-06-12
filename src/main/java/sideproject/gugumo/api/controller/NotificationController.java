package sideproject.gugumo.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.dto.notificationdto.NotificationDto;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 로그인 한 유저 sse 연결
     */
    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal CustomUserDetails principal,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(principal, lastEventId);
    }

    @GetMapping("/notification")
    public <T extends NotificationDto> ApiResponse<List<T>> findNoti(@AuthenticationPrincipal CustomUserDetails principal) {
        return ApiResponse.createSuccess(notificationService.findNotification(principal));
    }

    @PatchMapping("/notification/{noti_id}")
    public ApiResponse<String> read(@AuthenticationPrincipal CustomUserDetails principal,
                                    @PathVariable("noti_id") Long id) {

        notificationService.read(principal, id);
        return ApiResponse.createSuccess("알림 읽음처리 완료");
    }

    @PatchMapping("/notification")
    public ApiResponse<String> readAll(@AuthenticationPrincipal CustomUserDetails principal) {

        notificationService.readAll(principal);
        return ApiResponse.createSuccess("알림 모두 읽음처리 완료");
    }

    @DeleteMapping("/notification/{noti_id}")
    public ApiResponse<String> deleteNoti(@AuthenticationPrincipal CustomUserDetails principal,
                                          @PathVariable("noti_id") Long id) {
        notificationService.deleteNotification(principal, id);

        return ApiResponse.createSuccess("알림 삭제 완료");
    }
}