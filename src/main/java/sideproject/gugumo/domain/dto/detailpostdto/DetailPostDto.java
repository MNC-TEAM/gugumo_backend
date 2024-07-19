package sideproject.gugumo.domain.dto.detailpostdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
import sideproject.gugumo.domain.entity.meeting.MeetingStatus;
import sideproject.gugumo.domain.entity.meeting.MeetingType;

import java.time.LocalDate;
import java.time.LocalDateTime;


//장기, 단기에 따라 dto를 나눠서 전송

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public abstract class DetailPostDto {

    @Schema(description = "게시글 고유 번호")
    private Long postId;
    @Schema(description = "게시글 작성자")
    private String author;

    @Schema(description = "모임 단기 장기 여부", implementation = MeetingType.class)
    private MeetingType meetingType;
    @Schema(description = "모임 후 진행할 구기종목", implementation = GameType.class)
    private GameType gameType;
    @Schema(description = "모임 인원")
    private int meetingMemberNum;
    @Schema(description = "모집 제한 날짜")
    private LocalDate meetingDeadline;
    @Schema(description = "오픈카톡방 주소")
    private String openKakao;
    @Schema(description = "모임 지역", implementation = Location.class)
    private Location location;

    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "게시글 내용", maxLength = 10000)
    private String content;
    @Schema(description = "게시글 작성 일자")
    private LocalDateTime createdDateTime;

    @Schema(description = "모임 상태", implementation = MeetingStatus.class)
    private MeetingStatus meetingStatus;
    @Schema(description = "조회수")
    private long viewCount;

    @Schema(description = "본인 게시글 여부")
    private boolean isYours;
    @Schema(description = "북마크한 사용자 수")
    private long bookmarkCount;
    @Schema(description = "사용자의 북마크 여부")
    private boolean isBookmarked;

    @Schema(description = "게시글 작성자 회원 탈퇴 여부(닉네임 공개 여부 확인용)")
    private boolean isAuthorExpired;
}
