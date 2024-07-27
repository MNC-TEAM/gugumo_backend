package sideproject.gugumo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateCmntReq {

    @Schema(description = "댓글 내용", maxLength = 1000)
    String content;
}
