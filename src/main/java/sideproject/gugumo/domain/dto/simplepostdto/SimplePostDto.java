package sideproject.gugumo.domain.dto.simplepostdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
import sideproject.gugumo.domain.entity.meeting.MeetingStatus;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SimplePostDto {

    @Schema(description = "게시글 고유 번호")
    private Long postId;
    @Schema(description = "모임 상태", implementation = MeetingStatus.class)
    private MeetingStatus meetingStatus;
    @Schema(description = "모임 후 진행할 구기종목", implementation = GameType.class)
    private GameType gameType;
    @Schema(description = "모임 지역", implementation = Location.class)
    private Location location;
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "모임 인원")
    private int meetingMemberNum;
    @Schema(description = "모집 제한 날짜")
    private LocalDate meetingDeadline;
    @Schema(description = "북마크 여부")
    private boolean isBookmarked;
}
