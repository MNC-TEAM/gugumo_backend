package sideproject.gugumo.domain.dto.notificationdto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class PostNotificationDto extends NotificationDto{

    private Long postId;
    private String senderNick;
}
