package sideproject.gugumo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.gugumo.adaptor.MemberChecker;
import sideproject.gugumo.domain.dto.customnotidto.CustomNotiDto;
import sideproject.gugumo.domain.dto.customnotidto.PostCustomNotiDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.entity.notification.CustomNoti;
import sideproject.gugumo.domain.entity.notification.NotificationType;
import sideproject.gugumo.domain.entity.member.Member;
import sideproject.gugumo.exception.exception.NoAuthorizationException;
import sideproject.gugumo.exception.exception.NotificationNotFoundException;
import sideproject.gugumo.repository.CustomNotiRepository;
import sideproject.gugumo.repository.MemberRepository;
import sideproject.gugumo.repository.FcmNotificationTokenRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FcmNotificationService {


    private final MemberChecker memberChecker;
    private final FcmNotificationTokenRepository fcmNotificationTokenRepository;
    private final MemberRepository memberRepository;
    private final CustomNotiRepository customNotiRepository;


    public <T extends CustomNotiDto> List<T> findNotification(@AuthenticationPrincipal CustomUserDetails principal) {

        Member member = memberChecker.toMember(principal, "알림 조회 실패");
        List<CustomNoti> result = customNotiRepository.findByMemberOrderByCreateDateDesc(member);

        return result.stream()
                .map(n -> convertToDto(n))
                .map(n -> (T) n)
                .collect(Collectors.toList());
    }

    private <T extends CustomNotiDto> T convertToDto(CustomNoti customNoti) {

        if (customNoti.getNotificationType() == NotificationType.COMMENT) {
            return (T) PostCustomNotiDto.builder()
                    .id(customNoti.getId())
                    .message(customNoti.getMessage())
                    .notificationType(NotificationType.COMMENT)
                    .createDate(customNoti.getCreateDate())
                    .isRead(customNoti.isRead())
                    .postId(customNoti.getPostId())
                    .build();
        } else {
            return null;
        }

    }

    @Transactional
    public void read(CustomUserDetails principal, Long id) {
        Member member = memberChecker.toMember(principal, "알림 읽음처리 실패");

        CustomNoti notification = customNotiRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("알림 읽음처리 실패: 존재하지 않는 알림입니다.")
        );

        if (!notification.getMember().equals(member)) {
            throw new NoAuthorizationException("알림 읽음처리 실패: 권한이 없습니다.");
        }

        notification.read();

    }

    @Transactional
    public void readAll(CustomUserDetails principal) {
        Member member = memberChecker.toMember(principal, "알림 모두 읽음처리 실패");

        List<CustomNoti> notifications = customNotiRepository.findByMemberOrderByCreateDateDesc(member);

        for (CustomNoti notification : notifications) {
            notification.read();
        }

    }

    @Transactional
    public void deleteNotification(CustomUserDetails principal, Long id) {
        Member member = memberChecker.toMember(principal, "알림 삭제 실패");

        CustomNoti notification = customNotiRepository.findById(id).orElseThrow(
                () -> new NotificationNotFoundException("알림 삭제 실패: 존재하지 않는 알림입니다.")
        );

        customNotiRepository.delete(notification);

    }

    @Transactional
    public void deleteReadNotification(CustomUserDetails principal) {
        Member member = memberChecker.toMember(principal, "읽은 알림 삭제 실패");

        customNotiRepository.deleteAllByMemberAndIsReadTrue(member);

    }


}
