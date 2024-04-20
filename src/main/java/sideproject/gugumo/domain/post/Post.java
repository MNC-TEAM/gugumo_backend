package sideproject.gugumo.domain.post;

import jakarta.persistence.*;
import lombok.*;
import sideproject.gugumo.domain.Member;
import sideproject.gugumo.domain.meeting.Meeting;
import sideproject.gugumo.request.UpdatePostReq;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Column(length = 10000)
    private String content;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default
    private long viewCount = 0;


    @Builder.Default
    private boolean isDelete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 게시글 검색 기능에서 post와 meeting을 조인해야 함
     * 하지만 meeting에서 post를 추출하는 것은 의도 상 맞지 않는 것 같음
     * 따라서 1:1 양방향으로 변경
     */
    @OneToOne(mappedBy = "post")
    private Meeting meeting;

    public void update(UpdatePostReq updatePostReq) {

        this.title = updatePostReq.getTitle();
        this.content = updatePostReq.getContent();


    }

    //연관관계 편의 메서드용
    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public void tempDelete() {
        this.isDelete = true;
    }
}
