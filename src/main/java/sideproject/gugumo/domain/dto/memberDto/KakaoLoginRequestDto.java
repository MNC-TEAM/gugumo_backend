package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "카카오 로그인 요청")
public class KakaoLoginRequestDto {
    @Schema(description = "유저 이메일")
    private String username;
    @Schema(description = "유저 닉네임")
    private String nickname;
    @Schema(description = "카카오 ID")
    private Long kakaoId;
    @Schema(description = "프로필 사진 경로")
    private String profilePath;

    @Builder
    public KakaoLoginRequestDto(String username, String nickname, Long kakaoId, String profilePath) {
        this.username = username;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
        this.profilePath = profilePath;
    }
}
