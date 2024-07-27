package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.domain.dto.CmntDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.request.CreateCmntReq;
import sideproject.gugumo.request.UpdateCmntReq;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.CmntService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CmntController {

    private final CmntService cmntService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "댓글 등록", description = "댓글을 등록합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "댓글 등록 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"글 삭제 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"댓글 등록 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "댓글 등록 권한 없음",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"댓글 등록 실패: 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"댓글 등록 실패: 존재하지 않는 게시글입니다.\"}"
                                    )))
            })
    public ApiResponse<String> saveComment(@AuthenticationPrincipal CustomUserDetails principal,
                                           @Valid @RequestBody CreateCmntReq req) {
        cmntService.save(req, principal);

        return ApiResponse.createSuccess("댓글 저장 완료");

    }

    @GetMapping("/{post_id}")
    public ApiResponse<List<CmntDto>> findComment(@AuthenticationPrincipal CustomUserDetails principal,
                                                  @PathVariable("post_id") Long postId) {

        return ApiResponse.createSuccess(cmntService.findComment(postId, principal));
    }

    @PatchMapping("/{comment_id}")
    public ApiResponse<String> updateComment(@AuthenticationPrincipal CustomUserDetails principal,
                                             @PathVariable("comment_id") Long commentId,
                                             @RequestBody UpdateCmntReq req) {

        cmntService.updateComment(commentId, req, principal);

        return ApiResponse.createSuccess("댓글 갱신 완료");

    }

    @DeleteMapping("/{comment_id}")
    public ApiResponse<String> deleteComment(@AuthenticationPrincipal CustomUserDetails principal,
                                             @PathVariable("comment_id") Long commentId) {
        cmntService.deleteComment(commentId, principal);

        return ApiResponse.createSuccess("댓글 삭제 완료");

    }
}
