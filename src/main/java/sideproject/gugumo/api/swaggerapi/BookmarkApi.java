package sideproject.gugumo.api.swaggerapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
import sideproject.gugumo.swagger.simplepostdtoresponse.SimplePostLongDtoResponse;
import sideproject.gugumo.swagger.simplepostdtoresponse.SimplePostShortDtoResponse;

public interface BookmarkApi {
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
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 이미 등록된 북마크입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(name = "비로그인 사용자",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 비로그인 사용자입니다.\"}"
                                            ),
                                            @ExampleObject(name = "권한 없음",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 권한이 없습니다.\"}"
                                            )

                                    })),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 등록 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                    )))
            })
    public ApiResponse<String> saveBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody @Valid CreateBookmarkReq createBookmarkReq);

    @GetMapping
    @Operation(summary = "북마크 조회", description = "북마크한 게시글을 조회합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "북마크 게시글",
                            content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {SimplePostShortDtoResponse.class, SimplePostLongDtoResponse.class}),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                        "status": "success",
                                                        "data": {
                                                             "content": [
                                                                 {
                                                                     "postId": 1,
                                                                     "status": "RECRUIT",
                                                                     "gameType": "BASKETBALL",
                                                                     "location": "SEOUL",
                                                                     "title": "test1",
                                                                     "meetingDateTime": "1970-01-01T15:00:00",
                                                                     "meetingDays": "MON;WED;FRI",
                                                                     "meetingMemberNum": 3,
                                                                     "meetingDeadline": "2024-12-11"
                                                                 }
                                                             ],
                                                             "pageableCustom": {
                                                                 "number": 1,
                                                                 "size": 12,
                                                                 "sort": {
                                                                     "empty": true,
                                                                     "sorted": false,
                                                                     "unsorted": true
                                                                 },
                                                                 "first": true,
                                                                 "last": true,
                                                                 "hasNext": false,
                                                                 "totalPages": 1,
                                                                 "totalElements": 1,
                                                                 "numberOfElements": 1,
                                                                 "empty": false
                                                             }
                                                             
                                                        },
                                                        "message": null
                                                    }
                                                    """
                                    )))
            })
    public <T extends SimplePostDto> ApiResponse<PageCustom<T>> findBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PageableDefault(size = 12, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, value = "q", defaultValue = "") @Parameter(description = "검색어") String q);

    @DeleteMapping("/{bookmark_id}")
    @Operation(summary = "북마크 삭제", description = "북마크를 삭제합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "북마크 삭제 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"북마크 삭제 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(name = "비로그인 사용자",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 삭제 실패: 비로그인 사용자입니다.\"}"
                                            ),
                                            @ExampleObject(name = "권한 없음",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 삭제 실패: 권한이 없습니다.\"}"
                                            )
                                    })),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "존재하지 않는 리소스에 대한 요청",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(name = "북마크가 존재하지 않음",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 삭제 실패: 해당 북마크가 존재하지 않습니다.\"}"
                                            ),
                                            @ExampleObject(name = "게시글이 존재하지 않음",
                                                    value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"북마크 삭제 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                            )
                                    }))
            })
    public ApiResponse<String> deleteBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("bookmark_id") @Parameter(description = "삭제할 북마크 고유번호") Long bookmarkId);
}
