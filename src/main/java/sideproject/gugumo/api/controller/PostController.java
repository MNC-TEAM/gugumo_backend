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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sideproject.gugumo.cond.SortType;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.dto.detailpostdto.DetailPostDto;
import sideproject.gugumo.domain.dto.simplepostdto.SimplePostDto;
import sideproject.gugumo.domain.dto.simplepostdto.SimplePostLongDto;
import sideproject.gugumo.domain.dto.simplepostdto.SimplePostShortDto;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
import sideproject.gugumo.page.PageCustom;
import sideproject.gugumo.request.CreatePostReq;
import sideproject.gugumo.request.UpdatePostReq;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.PostService;

import java.util.List;


/**
 * /api/v1이 중복
 * 묶고 싶은데...
 * 커스텀 어노테이션: @RequestMapping("/meeting")과의 순서 보장? 애초에 실행은 되는가?
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meeting")
@Tag(name = "Post")
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "글쓰기", description = "게시글을 작성합니다.",
                responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "글 작성 완료",
                                                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                                                     examples = @ExampleObject(
                                                                             value = "{\"status\" : \"success\", \"data\" : \"글 작성 완료\", \"message\" : null}"
                                                                     ))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "게시글 저장 권한 없음",
                                                                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                                                     examples = @ExampleObject(
                                                                             value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"저장 실패: 게시글 저장 권한이 없습니다.\"}"
                                                                     )))
                })
    public ApiResponse<String> save(@AuthenticationPrincipal CustomUserDetails principal,
                                    @RequestBody @Valid CreatePostReq createPostReq) {
        postService.save(principal, createPostReq);

        return ApiResponse.createSuccess("글 작성 완료");
    }


    /**
     * 정렬(Sort)은 조건이 조금만 복잡해져도 Pageable의 Sort기능을 사용하기 어렵다. 루트 엔티티 범위를 넘어가는(join을 하는 등)
     * 동적 정렬 기능이 필요하면 스프링 데이터 페이징이 제공하는 Sort를 사용하기 보다는 파라미터를 받아서 직접 처리하는 것을 권장한다.
     */
    @GetMapping
    @Operation(summary = "게시글 조회", description = "주어진 조건에 맞는 게시글을 조회합니다.",
                responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게시글 정보",
                                                                    content=@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                                                    examples = @ExampleObject(value = """
                                                                            {
                                                                                "status": "success",
                                                                                   "data": {
                                                                                    "content": [
                                                                                        {
                                                                                            "postId": 4,
                                                                                            "meetingStatus": "RECRUIT",
                                                                                            "gameType": "BASKETBALL",
                                                                                            "location": "SEOUL",
                                                                                            "title": "test1",
                                                                                            "meetingMemberNum": 3,
                                                                                            "meetingDeadline": "2024-12-11",
                                                                                            "meetingDateTime": "2024-12-21T15:00:00",
                                                                                            "bookmarked": false
                                                                                        },
                                                                                        {
                                                                                            "postId": 3,
                                                                                            "meetingStatus": "RECRUIT",
                                                                                            "gameType": "BASKETBALL",
                                                                                            "location": "SEOUL",
                                                                                            "title": "test1",
                                                                                            "meetingMemberNum": 3,
                                                                                            "meetingDeadline": "2024-12-11",
                                                                                            "meetingDateTime": "2024-12-21T15:00:00",
                                                                                            "bookmarked": false
                                                                                        },
                                                                                        {
                                                                                            "postId": 2,
                                                                                            "meetingStatus": "RECRUIT",
                                                                                            "gameType": "BASKETBALL",
                                                                                            "location": "SEOUL",
                                                                                            "title": "test1",
                                                                                            "meetingMemberNum": 3,
                                                                                            "meetingDeadline": "2024-12-11",
                                                                                            "meetingTime": "15:00:00",
                                                                                            "meetingDays": "MON;WED;FRI",
                                                                                            "bookmarked": false
                                                                                        },
                                                                                        {
                                                                                            "postId": 1,
                                                                                            "meetingStatus": "RECRUIT",
                                                                                            "gameType": "BASKETBALL",
                                                                                            "location": "SEOUL",
                                                                                            "title": "test1",
                                                                                            "meetingMemberNum": 3,
                                                                                            "meetingDeadline": "2024-12-11",
                                                                                            "meetingTime": "15:00:00",
                                                                                            "meetingDays": "MON;WED;FRI",
                                                                                            "bookmarked": false
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
                                                                                        "totalElements": 4,
                                                                                        "numberOfElements": 4,
                                                                                        "empty": false
                                                                                    }
                                                                                },
                                                                                "message": null
                                                                            }
                                                                            """)))
                })
    public <T extends SimplePostDto> ApiResponse<PageCustom<T>> findPostSimple(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PageableDefault(size = 12) @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false, value = "q") @Parameter(description = "검색어") String q,
            @RequestParam(required = false, value = "location") @Parameter(description = "모임 지역", schema = @Schema(implementation = Location.class)) String location,
            @RequestParam(required = false, value = "gametype") @Parameter(description = "종목", schema = @Schema(implementation = GameType.class)) String gameType,
            @RequestParam(required = false, value = "meetingstatus", defaultValue = "RECRUIT") @Parameter(description = "종목", schema = @Schema(allowableValues = {"RECRUIT, END, ALL"})) String meetingStatus,
            @RequestParam(required = false, value = "sort", defaultValue = "NEW") @Parameter(description = "정렬 조건", schema = @Schema(implementation = SortType.class)) String sortType) {


        return ApiResponse.createSuccess(postService.findSimplePost(principal, pageable, q, gameType, location, meetingStatus, sortType));
    }

    @GetMapping("/{post_id}")
    @Operation(summary = "게시글 상세 조회", description = "게시글의 상세 정보를 조회합니다.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "글 상세 내용",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "status": "success",
                                                    "data": {
                                                        "postId": 2,
                                                        "author": "testnick",
                                                        "meetingType": "SHORT",
                                                        "gameType": "BADMINTON",
                                                        "meetingMemberNum": 1,
                                                        "meetingDeadline": "2024-05-06",
                                                        "openKakao": "open",
                                                        "location": "GYEONGGI",
                                                        "title": "테스트 asdf제목입니다.",
                                                        "content": "테스트 내용입니다.",
                                                        "createdDateTime": "2024-05-21T20:09:47.373269",
                                                        "meetingStatus": "RECRUIT",
                                                        "viewCount": 1,
                                                        "bookmarkCount": 0,
                                                        "meetingDateTime": "2024-05-06T15:00:00",
                                                        "bookmarked": false,
                                                        "authorExpired": false,
                                                        "yours": true
                                                    },
                                                    "message": null
                                                }
                                                """
                                ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"조회 실패: 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"조회 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                    )))

            })
    public <T extends DetailPostDto> ApiResponse<T> findPostDetail(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("post_id") @Parameter(description = "조회할 게시글 고유번호") Long postId) {
        DetailPostDto detailPostDto = postService.findDetailPostByPostId(principal, postId);

        return ApiResponse.createSuccess((T) detailPostDto);
    }

    @PatchMapping("/{post_id}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "글 갱신 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"글 갱신 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"수정 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "게시글 수정 권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"수정 실패: 게시글 수정 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"수정 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                    )))

    })
    public ApiResponse<String> updatePost(@AuthenticationPrincipal CustomUserDetails principal,
                                          @PathVariable("post_id") @Parameter(description = "수정할 게시글 고유번호") Long postId,
                                          @RequestBody @Valid UpdatePostReq updatePostReq) {
        postService.update(principal, postId, updatePostReq);

        return ApiResponse.createSuccess("글 갱신 완료");
    }


    @DeleteMapping("/{post_id}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "글 삭제 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"글 삭제 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"삭제 실패: 비로그인 사용자입니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "게시글 삭제 권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"삭제 실패: 게시글 삭제 권한이 없습니다.\"}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"삭제 실패: 해당 게시글이 존재하지 않습니다.\"}"
                                    )))
            })
    public ApiResponse<String> deletePost(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("post_id") Long postId) {

        postService.deletePost(principal, postId);

        return ApiResponse.createSuccess("글 삭제 완료");
    }


    @GetMapping("/my")
    @Operation(summary = "유저 게시글 조회", description = "내가 작성한 게시글을 조회합니다.",
            responses = {
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "유저 게시글 리스트",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                                "status": "success",
                                                "data": {
                                                    "content": [
                                                        {
                                                            "postId": 2,
                                                            "meetingStatus": "RECRUIT",
                                                            "gameType": "BASKETBALL",
                                                            "location": "SEOUL",
                                                            "title": "test21",
                                                            "meetingMemberNum": 3,
                                                            "meetingDeadline": "2024-12-11",
                                                            "meetingTime": "15:00:00",
                                                            "meetingDays": "MON;WED;FRI",
                                                            "bookmarked": false
                                                        },
                                                        {
                                                            "postId": 1,
                                                            "meetingStatus": "RECRUIT",
                                                            "gameType": "BASKETBALL",
                                                            "location": "SEOUL",
                                                            "title": "test21",
                                                            "meetingMemberNum": 3,
                                                            "meetingDeadline": "2024-12-11",
                                                            "meetingTime": "15:00:00",
                                                            "meetingDays": "MON;WED;FRI",
                                                            "bookmarked": false
                                                        }
                                                    ],
                                                    "pageable": {
                                                        "number": 1,
                                                        "size": 12,
                                                        "sort": {
                                                            "empty": false,
                                                            "sorted": true,
                                                            "unsorted": false
                                                        },
                                                        "first": true,
                                                        "last": true,
                                                        "hasNext": false,
                                                        "totalPages": 1,
                                                        "totalElements": 2,
                                                        "numberOfElements": 2,
                                                        "empty": false
                                                    }
                                                },
                                                "message": null
                                            }
                                            """
                            ))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "비로그인 사용자",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                examples = @ExampleObject(
                                        value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"내 글 조회 실패: 비로그인 사용자입니다.\"}"
                                ))),
                @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "내 글 조회 권한 없음",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                examples = @ExampleObject(
                                        value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"내 글 조회 실패: 접근 권한이 없습니다.\"}"
                                )))

            })
    public <T extends SimplePostDto> ApiResponse<PageCustom<T>> findMyPost(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PageableDefault(size = 12, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, value = "q", defaultValue = "") String q) {
        return ApiResponse.createSuccess(postService.findMyPost(principal, pageable, q));

    }

    @GetMapping("/recommend")
    @Operation(summary = "추천 게시글 조회", description = "선호 종목에 맞춘 추천 게시글을 조회합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "유저 게시글 리스트",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                         "status": "success",
                                                         "data": {
                                                             "content": [
                                                                 {
                                                                     "postId": 2,
                                                                     "meetingStatus": "RECRUIT",
                                                                     "gameType": "BASKETBALL",
                                                                     "location": "SEOUL",
                                                                     "title": "test21",
                                                                     "meetingMemberNum": 3,
                                                                     "meetingDeadline": "2024-12-11",
                                                                     "meetingTime": "15:00:00",
                                                                     "meetingDays": "MON;WED;FRI",
                                                                     "bookmarked": false
                                                                 },
                                                                 {
                                                                     "postId": 1,
                                                                     "meetingStatus": "RECRUIT",
                                                                     "gameType": "BASKETBALL",
                                                                     "location": "SEOUL",
                                                                     "title": "test21",
                                                                     "meetingMemberNum": 3,
                                                                     "meetingDeadline": "2024-12-11",
                                                                     "meetingTime": "15:00:00",
                                                                     "meetingDays": "MON;WED;FRI",
                                                                     "bookmarked": false
                                                                 }
                                                             ],
                                                             "pageable": {
                                                                 "number": 1,
                                                                 "size": 12,
                                                                 "sort": {
                                                                     "empty": false,
                                                                     "sorted": true,
                                                                     "unsorted": false
                                                                 },
                                                                 "first": true,
                                                                 "last": true,
                                                                 "hasNext": false,
                                                                 "totalPages": 1,
                                                                 "totalElements": 2,
                                                                 "numberOfElements": 2,
                                                                 "empty": false
                                                             }
                                                         },
                                                         "message": null
                                                     }
                                                    """
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "추천글 조회 권한 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"추천글 조회 실패: 권한이 없습니다.\"}"
                                    )))
            })
    public <T extends SimplePostDto> ApiResponse<List<T>> findRecommendPost(
            @AuthenticationPrincipal CustomUserDetails principal) {
        return ApiResponse.createSuccess(postService.findRecommendPost(principal));
    }


}
