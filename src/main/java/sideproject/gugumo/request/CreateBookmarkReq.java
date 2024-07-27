package sideproject.gugumo.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBookmarkReq {

    @NotNull
    @Schema(description = "북마크할 게시글의 고유 번호")
    private Long postId;
}
