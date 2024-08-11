package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "비밀번호 변경 요청")
public class UpdateMemberPasswordDto {
    @Schema(description = "변경할 비밀번호")
    String password;

    public UpdateMemberPasswordDto() {
    }

    @Builder
    public UpdateMemberPasswordDto(String password) {
        this.password = password;
    }
}
