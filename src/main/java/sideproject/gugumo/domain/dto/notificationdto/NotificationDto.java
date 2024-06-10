package sideproject.gugumo.domain.dto.notificationdto;

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
    private LocalDateTime createDate;


    public static NotificationDto createResponse(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .name(notification.getMember().getNickname())
                .content(notification.getContent())
                .createDate(notification.getCreateDate())
                .build();
    }

}
