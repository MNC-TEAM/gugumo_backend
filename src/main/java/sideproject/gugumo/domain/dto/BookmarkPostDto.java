package sideproject.gugumo.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
import sideproject.gugumo.domain.entity.meeting.MeetingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookmarkPostDto {

    private Long postId;
    private MeetingStatus status;
    private GameType gameType;
    private Location location;
    private String title;
    private LocalDateTime meetingDateTime;
    private String meetingDays;
    private int meetingMemberNum;
    private LocalDate meetingDeadline;
}
