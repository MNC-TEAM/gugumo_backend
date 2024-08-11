package sideproject.gugumo.domain.dto.emailDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@Schema(description = "이메일 인증 요청")
public class EmailCheckDto {
    @Email
    @NotEmpty(message = "이메일을 입력해 주세요.")
    @Schema(description = "인증 대상 이메일", example = "email@email.com")
    private String email;

    @NotEmpty(message = "인증 번호를 입력해 주세요.")
    @Schema(description = "인증 번호")
    private String emailAuthNum;
}
