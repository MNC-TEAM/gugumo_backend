package sideproject.gugumo.domain.entity.notification;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sideproject.gugumo.domain.entity.member.Member;


import java.time.LocalDateTime;


//당연히 이걸 직접 보내지 않음
@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;

    private String message;


    @Builder.Default
    private boolean isRead = false;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      //알림 수신자

}
