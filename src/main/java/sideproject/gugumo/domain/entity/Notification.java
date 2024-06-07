package sideproject.gugumo.domain.entity;

import jakarta.persistence.*;
import lombok.*;


//당연히 이걸 직접 보내지 않음
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;


    @Builder.Default
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      //알림 수신자

}
