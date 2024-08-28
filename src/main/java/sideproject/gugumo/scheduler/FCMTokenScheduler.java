package sideproject.gugumo.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sideproject.gugumo.domain.entity.notification.FcmNotificationToken;
import sideproject.gugumo.repository.FcmNotificationTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMTokenScheduler {

    private final FcmNotificationTokenRepository fcmNotificationTokenRepository;

    //최근 사용 2달이 지난 토큰 삭제
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    @Transactional
    public void setExpiredMeetingStatus() {

        LocalDateTime expire = LocalDateTime.now().minusMonths(2);


        fcmNotificationTokenRepository.deleteExpiredToken(expire);


    }
}
