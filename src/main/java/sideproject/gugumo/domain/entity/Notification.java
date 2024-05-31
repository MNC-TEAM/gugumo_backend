package sideproject.gugumo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;


//당연히 이걸 직접 보내지 않음
@Entity
@Getter
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String content;

    //알림의 원인이 되는 댓글의 고유 번호
    private Long commentId;

    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;      //알림 수신자

}
