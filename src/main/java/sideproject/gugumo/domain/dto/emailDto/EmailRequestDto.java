package sideproject.gugumo.domain.dto.emailDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
@Schema(description = "인증번호 전송 요청")
public class EmailRequestDto {
    @Email
    @NotEmpty
    @Schema(description = "인증번호를 받을 이메일", example = "email@email.com")
    private String email;
}
