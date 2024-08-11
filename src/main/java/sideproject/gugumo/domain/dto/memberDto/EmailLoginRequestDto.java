package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "이메일 로그인 요청")
public class EmailLoginRequestDto {
    @Schema(description = "사용자 이메일", example = "username@email.com")
    private String username;
    @Schema(description = "비밀번호")
    private String password;
}
