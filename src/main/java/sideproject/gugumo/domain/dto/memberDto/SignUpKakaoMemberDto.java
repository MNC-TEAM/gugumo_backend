package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import sideproject.gugumo.domain.entity.meeting.GameType;

@Getter
@Schema(description = "카카오 회원가입 요청")
public class SignUpKakaoMemberDto {
    @Schema(description = "유저 이메일")
    private String username;
    @Schema(description = "유저 닉네임")
    private String nickname;
    @Schema(description = "유저 선호 구기 종목", implementation = GameType.class)
    private String favoriteSports;
    @Schema(description = "유저 카카오 ID")
    private Long kakaoId;
    // 서비스 이용 약관 동의 여부
    @Schema(description = "서비스 이용 약관 동의 여부")
    boolean isAgreeTermsUse;
    // 개인정보 수집 및 이용 동의
    @Schema(description = "개인정보 수집 및 이용 동의 여부")
    boolean isAgreeCollectingUsingPersonalInformation;
    // 마케팅 수신 동의
    @Schema(description = "마케팅 수신 동의 여부")
    boolean isAgreeMarketing;

    @Builder
    public SignUpKakaoMemberDto(String username, String nickname, String favoriteSports, Long kakaoId, boolean isAgreeTermsUse, boolean isAgreeCollectingUsingPersonalInformation, boolean isAgreeMarketing) {
        this.username = username;
        this.nickname = nickname;
        this.favoriteSports = favoriteSports;
        this.kakaoId = kakaoId;
        this.isAgreeTermsUse = isAgreeTermsUse;
        this.isAgreeCollectingUsingPersonalInformation = isAgreeCollectingUsingPersonalInformation;
        this.isAgreeMarketing = isAgreeMarketing;
    }
}
