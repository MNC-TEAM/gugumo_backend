package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.api.swaggerapi.CmntApi;
import sideproject.gugumo.domain.dto.CmntDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.request.CreateCmntReq;
import sideproject.gugumo.request.UpdateCmntReq;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.CmntService;
import sideproject.gugumo.swagger.CmntDtoResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Tag(name = "Cmnt")
public class CmntController implements CmntApi {

    private final CmntService cmntService;

    @PostMapping("/new")
    public ApiResponse<String> saveComment(@AuthenticationPrincipal CustomUserDetails principal,
                                           @Valid @RequestBody CreateCmntReq req) {

        cmntService.save(req, principal);

        return ApiResponse.createSuccess("댓글 저장 완료");

    }

    @GetMapping("/{post_id}")
    public ApiResponse<List<CmntDto>> findComment(@AuthenticationPrincipal CustomUserDetails principal,
                                                  @PathVariable("post_id") @Parameter(description = "게시글 고유번호") Long postId) {

        return ApiResponse.createSuccess(cmntService.findComment(postId, principal));
    }

    @PatchMapping("/{comment_id}")
    public ApiResponse<String> updateComment(@AuthenticationPrincipal CustomUserDetails principal,
                                             @PathVariable("comment_id") @Parameter(description = "댓글 고유 번호") Long commentId,
                                             @RequestBody UpdateCmntReq req) {

        cmntService.updateComment(commentId, req, principal);

        return ApiResponse.createSuccess("댓글 갱신 완료");

    }

    @DeleteMapping("/{comment_id}")
    public ApiResponse<String> deleteComment(@AuthenticationPrincipal CustomUserDetails principal,
                                             @PathVariable("comment_id") @Parameter(description = "댓글 고유 번호") Long commentId) {
        cmntService.deleteComment(commentId, principal);

        return ApiResponse.createSuccess("댓글 삭제 완료");

    }
}
