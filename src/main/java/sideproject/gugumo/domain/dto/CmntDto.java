package sideproject.gugumo.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CmntDto {

    @Schema(description = "댓글 고유 번호")
    private Long commentId;
    @Schema(description = "댓글 작성자 닉네임")
    private String author;
    @Schema(description = "댓글 작성자 본인 여부")
    private boolean isYours;
    @Schema(description = "댓글 작성자 탈퇴 여부")
    private boolean isAuthorExpired;
    @Schema(description = "댓글 내용", maxLength = 1000)
    private String content;
    @Schema(description = "댓글 작성 시각")
    private LocalDateTime createdDateTime;
    @Schema(description = "대댓글 여부")
    private boolean isNotRoot;
    @Schema(description = "대댓글의 경우 부모 댓글의 고유 번호")
    private Long parentCommentId;       //부모가 존재하지 않으면 null 반환->값을 주지 않음
    @Schema(description = "댓글 정렬 순서")
    private long orderNum;



    @QueryProjection
    public CmntDto(Long commentId, String author, boolean isYours, boolean isAuthorExpired, String content, LocalDateTime createdDateTime, boolean isNotRoot, Long parentCommentId, long orderNum) {
        this.commentId = commentId;
        this.author = author;
        this.isYours = isYours;
        this.isAuthorExpired = isAuthorExpired;
        this.content = content;
        this.createdDateTime = createdDateTime;
        this.isNotRoot = isNotRoot;
        this.parentCommentId = parentCommentId;
        this.orderNum = orderNum;
    }
}
