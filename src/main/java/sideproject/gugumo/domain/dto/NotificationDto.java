package sideproject.gugumo.domain.dto;

import lombok.*;
import sideproject.gugumo.domain.entity.Notification;

@Getter
@Builder
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    private String name;
    private String content;


    public static NotificationDto createResponse(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .name(notification.getMember().getNickname())
                .content(notification.getContent())
                .build();
    }

}
