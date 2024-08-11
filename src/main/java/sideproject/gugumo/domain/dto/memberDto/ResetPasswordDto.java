package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "비밀번호 초기화 요청")
public class ResetPasswordDto {
    @Schema(description = "임시 비밀번호를 받을 이메일")
    String email;
}
