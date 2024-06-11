package sideproject.gugumo.domain.dto.notificationdto;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sideproject.gugumo.domain.entity.notification.Notification;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {

    private Long id;
    private String name;
    private String content;
    private String message;
    private LocalDateTime createDate;
    private boolean isRead;



}
