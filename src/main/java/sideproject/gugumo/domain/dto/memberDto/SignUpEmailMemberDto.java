package sideproject.gugumo.domain.dto.memberDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideproject.gugumo.domain.entity.meeting.GameType;

@Getter
@NoArgsConstructor
@Schema(description = "이메일 회원가입 요청")
public class SignUpEmailMemberDto {
    @Schema(description = "회원 닉네임")
    private String nickname;
    @Schema(description = "회원 이메일")
    private String username;
    @Schema(description = "비밀번호")
    private String password;
    @Schema(description = "회원 선호 구기종목", implementation = GameType.class)
    private String favoriteSports;
    @Schema(description = "이메일 인증 번호")
    private String emailAuthNum;
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
    public SignUpEmailMemberDto(String nickname, String username, String password, String favoriteSports, String emailAuth, boolean isAgreeTermsUse, boolean isAgreeCollectingUsingPersonalInformation, boolean isAgreeMarketing) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.favoriteSports = favoriteSports;
        this.emailAuthNum = emailAuth;
        this.isAgreeTermsUse = isAgreeTermsUse;
        this.isAgreeCollectingUsingPersonalInformation = isAgreeCollectingUsingPersonalInformation;
        this.isAgreeMarketing = isAgreeMarketing;
    }
}
