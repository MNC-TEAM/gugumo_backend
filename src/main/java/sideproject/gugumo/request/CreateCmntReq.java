package sideproject.gugumo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateCmntReq {

    @NotNull
    @Schema(description = "게시글 고유번호")
    private Long postId;
    @NotEmpty
    @Schema(description = "댓글 내용")
    private String content;
    @Schema(description = "대댓글일 경우 부모 댓글의 고유번호")
    private Long parentCommentId;


}
