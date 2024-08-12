package sideproject.gugumo.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.domain.dto.customnotidto.CustomNotiDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.FcmNotificationService;
import sideproject.gugumo.swagger.customnotidtoresponse.PostCustomNotiDtoResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "FcmNotification")
public class FcmNotificationController {


    private final FcmNotificationService fcmNotificationService;


    //몇개?
    @GetMapping("/notification")
    @Operation(summary = "알림 조회", description = "알림을 조회합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 정보",
                            content=@Content(mediaType = "application/json", schema = @Schema(oneOf = {PostCustomNotiDtoResponse.class}),
                                    examples = @ExampleObject(value = """
                                            {
                                                "status": "success",
                                                "data": [
                                                    {
                                                        "id": 652,
                                                        "content": "testComment",
                                                        "notificationType": "COMMENT",
                                                        "createDate": "2024-06-13T19:30:35.40451",
                                                        "postId": 1,
                                                        "read": false
                                                    },
                                                    {
                                                        "id": 605,
                                                        "content": "testComment",
                                                        "notificationType": "COMMENT",
                                                        "createDate": "2024-06-13T03:27:17.082465",
                                                        "postId": 1,
                                                        "read": false
                                                    },
                                                    {
                                                        "id": 604,
                                                        "content": "testComment",
                                                        "notificationType": "COMMENT",
                                                        "createDate": "2024-06-13T03:27:13.773505",
                                                        "postId": 1,
                                                        "read": false
                                                    }
                                                   ],
                                              "message": null
                                              }
                                                                            """))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 조회 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 조회 실패: 권한이 없습니다.\"}"
                                    )))
            })
    public <T extends CustomNotiDto> ApiResponse<List<T>> findNoti(@AuthenticationPrincipal CustomUserDetails principal) {
        return ApiResponse.createSuccess(fcmNotificationService.findNotification(principal));
    }


    @PatchMapping("/notification/read/{noti_id}")
    @Operation(summary = "알림 읽음처리", description = "알림 하나를 읽음처리 합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 읽음처리",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"알림 읽음처리 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 읽음처리 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 읽음처리 실패: 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 알림",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 읽음처리 실패: 존재하지 않는 알림입니다.\"}"
                                    )))
            })
    public ApiResponse<String> read(@AuthenticationPrincipal CustomUserDetails principal,
                                    @PathVariable("noti_id") Long id) {
        fcmNotificationService.read(principal, id);
        return ApiResponse.createSuccess("알림 읽음처리 완료");
    }

    @PatchMapping("/notification/read")
    @Operation(summary = "알림 모두 읽음처리", description = "해당 유저의 모든 알림을 읽음처리 합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 모두 읽음처리",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"알림 모두 읽음처리 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 모두 읽음처리 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 모두 읽음처리 실패: 권한이 없습니다.\"}"
                                    )))
            })
    public ApiResponse<String> readAll(@AuthenticationPrincipal CustomUserDetails principal) {

        fcmNotificationService.readAll(principal);
        return ApiResponse.createSuccess("알림 모두 읽음처리 완료");
    }

    @DeleteMapping("/notification/{noti_id}")
    @Operation(summary = "알림 삭제", description = "알림 하나를 삭제합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "알림 삭제",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"알림 삭제 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 삭제 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 삭제 실패: 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 알림",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"알림 삭제 실패: 존재하지 않는 알림입니다.\"}"
                                    )))
            })
    public ApiResponse<String> deleteNoti(@AuthenticationPrincipal CustomUserDetails principal,
                                          @PathVariable("noti_id") Long id) {
        fcmNotificationService.deleteNotification(principal, id);

        return ApiResponse.createSuccess("알림 삭제 완료");
    }


    @DeleteMapping("/notification/read")
    @Operation(summary = "읽은 알림 삭제", description = "해당 유저의 모든 읽은 알림을 삭제합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "읽은 알림 삭제",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"읽은 알림 삭제 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"읽은 알림 삭제 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"읽은 알림 삭제 실패: 권한이 없습니다.\"}"
                                    )))
            })
    public ApiResponse<String> deleteReadNoti(@AuthenticationPrincipal CustomUserDetails principal) {
        fcmNotificationService.deleteReadNotification(principal);

        return ApiResponse.createSuccess("읽은 알림 삭제 완료");
    }
}
