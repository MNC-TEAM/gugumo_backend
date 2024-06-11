package sideproject.gugumo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sideproject.gugumo.domain.dto.notificationdto.NotificationDto;
import sideproject.gugumo.domain.dto.memberDto.CustomUserDetails;
import sideproject.gugumo.domain.dto.notificationdto.PostNotificationDto;
import sideproject.gugumo.domain.entity.member.Member;
import sideproject.gugumo.domain.entity.member.MemberStatus;
import sideproject.gugumo.domain.entity.notification.Notification;
import sideproject.gugumo.domain.entity.notification.NotificationType;
import sideproject.gugumo.exception.exception.NoAuthorizationException;
import sideproject.gugumo.exception.exception.NotificationNotFoundException;
import sideproject.gugumo.repository.EmitterRepository;
import sideproject.gugumo.repository.MemberRepository;
import sideproject.gugumo.repository.NotificationRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    @Transactional
    public SseEmitter subscribe(CustomUserDetails principal, String lastEventId) {



        String emitterId = principal.getUsername() + "_" + System.currentTimeMillis();

        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 전송
        String eventId = principal.getUsername() + "_" + System.currentTimeMillis();
        sendNotification(emitter, eventId, emitterId,
                "EventStream Created. [userEmail=" + principal.getUsername() + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
        if (hasLostData(lastEventId)) {
            log.info("sendLostData={}", lastEventId);
            sendLostData(lastEventId, principal.getUsername(), emitterId, emitter);
        }

        return emitter;

    }


    @Transactional
    public void send(Member receiver, String content, String message) {

        Notification notification = Notification.builder()
                .member(receiver)
                .content(content)
                .message(message)
                .build();

        notificationRepository.save(notification);

        String receiverEmail = receiver.getUsername();
        String eventId = receiverEmail + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    NotificationDto resp = NotificationDto.builder()
                            .id(notification.getId())
                            .name(notification.getMember().getNickname())
                            .content(notification.getContent())
                            .message(notification.getMessage())
                            .createDate(notification.getCreateDate())
                            .isRead(notification.isRead())
                            .build();
                    sendNotification(emitter, eventId, key, resp);
                }
        );
    }

    //게시글 관련 정보 전송
    @Transactional
    public void send(Member receiver, String content, String message, Long postId, String senderNick) {

        Notification notification = Notification.builder()
                .member(receiver)
                .content(content)
                .message(message)
                .notificationType(NotificationType.POST)
                .postId(postId)
                .senderNick(senderNick)
                .build();

        notificationRepository.save(notification);

        String receiverEmail = receiver.getUsername();
        String eventId = receiverEmail + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByMemberId(receiverEmail);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    Notification resp = Notification.builder()
                            .id(notification.getId())
                            .content(notification.getContent())
                            .message(notification.getMessage())
                            .notificationType(NotificationType.POST)
                            .createDate(notification.getCreateDate())
                            .postId(notification.getPostId())
                            .senderNick(notification.getSenderNick())
                            .build();
                    sendNotification(emitter, eventId, key, resp);
                }
        );
    }

    public <T extends NotificationDto> List<T> findNotification(@AuthenticationPrincipal CustomUserDetails principal) {

        Member member = checkMemberValid(principal, "알림 조회 실패: 비로그인 사용자입니다.",
                "알림 조회 실패: 권한이 없습니다.");

        List<Notification> result = notificationRepository.findByMember(member);

        return result.stream()
                .map(n -> convertToDto(n))
                .map(n -> (T) n)
                .collect(Collectors.toList());
    }

    public <T extends NotificationDto> T convertToDto(Notification notification) {

        if (notification.getNotificationType() == NotificationType.POST) {
            return (T) PostNotificationDto.builder()
                    .id(notification.getId())
                    .name(notification.getMember().getNickname())
                    .content(notification.getContent())
                    .notificationType(NotificationType.POST)
                    .message(notification.getMessage())
                    .createDate(notification.getCreateDate())
                    .isRead(notification.isRead())
                    .senderNick(notification.getSenderNick())
                    .postId(notification.getPostId())
                    .build();
        } else {
            return null;
        }

    }


    public void deleteNotification(@AuthenticationPrincipal CustomUserDetails principal, Long id) {
        Member member = checkMemberValid(principal, "알림 삭제 실패: 비로그인 사용자입니다.",
                "알림 삭제 실패: 권한이 없습니다.");

        Notification notification = notificationRepository.findById(id).orElseThrow(
                ()->new NotificationNotFoundException("알림 삭제 실패: 존재하지 않는 알림입니다.")
        );

        notificationRepository.delete(notification);

    }

    private Member checkMemberValid(CustomUserDetails principal, String noLoginMessage, String notValidUserMessage) {
        if (principal == null) {
            throw new NoAuthorizationException(noLoginMessage);
        }

        Member author = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new NoAuthorizationException(notValidUserMessage));

        if (author.getStatus() != MemberStatus.active) {
            throw new NoAuthorizationException(notValidUserMessage);
        }
        return author;
    }


    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, String userEmail, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByMemberId(userEmail);
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .name("sse")
                    .data(data)
            );
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }
}
