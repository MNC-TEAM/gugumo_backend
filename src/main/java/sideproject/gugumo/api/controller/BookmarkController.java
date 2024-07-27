package sideproject.gugumo.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.dto.simplepostdto.SimplePostDto;
import sideproject.gugumo.page.PageCustom;
import sideproject.gugumo.request.CreateBookmarkReq;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.BookmarkService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;


    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "북마크 등록", description = "북마크를 등록합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "북마크 생성 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"북마크 생성 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "북마크 중복 등록",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 이미 등록된 북마크입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "북마크 등록 권한 없음",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                    )))
            })
    public ApiResponse<String> saveBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid CreateBookmarkReq createBookmarkReq) {

        bookmarkService.save(principal, createBookmarkReq);

        return ApiResponse.createSuccess("북마크 생성 완료");
    }

    @GetMapping
    public <T extends SimplePostDto> ApiResponse<PageCustom<T>> findBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PageableDefault(size = 12, sort="id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, value = "q", defaultValue = "") String q) {

        return ApiResponse.createSuccess(bookmarkService.findBookmarkByMember(principal, pageable, q));
    }

    @DeleteMapping("/{bookmark_id}")
    public ApiResponse<String> deleteBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("bookmark_id") Long bookmarkId) {
        bookmarkService.delete(bookmarkId, principal);

        return ApiResponse.createSuccess("북마크 삭제 완료");
    }

}
