package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "닉네임 변경 요청")
public class UpdateMemberNicknameDto {

    @Schema(description = "변경할 닉네임")
    private String nickname;

    public UpdateMemberNicknameDto() {
    }

    @Builder
    public UpdateMemberNicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
