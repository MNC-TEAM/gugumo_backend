package sideproject.gugumo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.gugumo.adaptor.MemberChecker;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.entity.notification.FcmNotificationToken;
import sideproject.gugumo.domain.entity.member.Member;
import sideproject.gugumo.repository.FcmNotificationTokenRepository;
import sideproject.gugumo.repository.MemberRepository;
import sideproject.gugumo.request.FcmTokenDto;

@Service
@RequiredArgsConstructor
public class FcmNotificationTokenService {

    private final MemberChecker memberChecker;
    private final FcmNotificationTokenRepository fcmNotificationTokenRepository;
    private final MemberRepository memberRepository;

    //토큰 저장
    @Transactional
    public void subscribe(CustomUserDetails principal, FcmTokenDto fcmTokenDto) {

        Member member = memberChecker.toMember(principal, "FCM 토큰 등록 실패");

        //token이 있으면->createDate update?
        if (fcmNotificationTokenRepository.existsByToken(fcmTokenDto.getFcmToken())) {
            FcmNotificationToken updateToken = fcmNotificationTokenRepository.findByToken(fcmTokenDto.getFcmToken()).get();

            updateToken.updateDate();
            updateToken.setMember(member);


        } else {
            //새로운 토큰일 경우 db에 저장
            FcmNotificationToken fcmNotificationToken = FcmNotificationToken.builder()
                    .token(fcmTokenDto.getFcmToken())
                    .member(member)
                    .build();

            fcmNotificationTokenRepository.save(fcmNotificationToken);
        }

    }


}
