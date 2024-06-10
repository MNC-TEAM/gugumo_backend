package sideproject.gugumo.domain.entity.notification;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
public class PostNotification extends Notification{
    private Long postId;
}
