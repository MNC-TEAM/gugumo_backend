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
import sideproject.gugumo.api.swaggerapi.BookmarkApi;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.dto.simplepostdto.SimplePostDto;
import sideproject.gugumo.page.PageCustom;
import sideproject.gugumo.request.CreateBookmarkReq;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.BookmarkService;
import sideproject.gugumo.swagger.simplepostdtoresponse.SimplePostLongDtoResponse;
import sideproject.gugumo.swagger.simplepostdtoresponse.SimplePostShortDtoResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
@Tag(name = "Bookmark")
public class BookmarkController implements BookmarkApi {

    private final BookmarkService bookmarkService;


    @PostMapping("/new")
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
            @RequestParam(required = false, value = "q", defaultValue = "") @Parameter(description = "검색어") String q) {

        return ApiResponse.createSuccess(bookmarkService.findBookmarkByMember(principal, pageable, q));
    }

    @DeleteMapping("/{bookmark_id}")
    public ApiResponse<String> deleteBookmark(
            @AuthenticationPrincipal CustomUserDetails principal,
            @PathVariable("bookmark_id") @Parameter(description = "삭제할 북마크 고유번호") Long bookmarkId) {

        bookmarkService.delete(bookmarkId, principal);

        return ApiResponse.createSuccess("북마크 삭제 완료");
    }

}
