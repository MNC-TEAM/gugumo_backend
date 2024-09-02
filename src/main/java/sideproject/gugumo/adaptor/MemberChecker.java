package sideproject.gugumo.adaptor;

import org.springframework.stereotype.Component;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.entity.member.Member;
import sideproject.gugumo.domain.entity.member.MemberStatus;
import sideproject.gugumo.exception.exception.NoAuthorizationException;
import sideproject.gugumo.repository.MemberRepository;

@Component
public class MemberChecker {

    private final String NO_LOGIN_MESSAGE = ": 비로그인 사용자입니다.";
    private final String NOT_VALID_USER_MESSAGE = ": 권한이 없습니다.";

    private final MemberRepository memberRepository;

    public MemberChecker(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member toMember(CustomUserDetails principal, String job) {

        if (principal == null) {
            throw new NoAuthorizationException(job + NO_LOGIN_MESSAGE);
        }

        //토큰에서
        Member author = memberRepository.findOne(principal.getId())
                .orElseThrow(
                        () -> new NoAuthorizationException(job + NOT_VALID_USER_MESSAGE)
                );

        if (author.getStatus() != MemberStatus.active) {
            throw new NoAuthorizationException(job + NOT_VALID_USER_MESSAGE);
        }
        return author;

    }

}
