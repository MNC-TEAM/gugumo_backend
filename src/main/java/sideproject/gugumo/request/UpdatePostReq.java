package sideproject.gugumo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
import sideproject.gugumo.domain.entity.meeting.MeetingStatus;
import sideproject.gugumo.domain.entity.meeting.MeetingType;
import sideproject.gugumo.validate.Conditional;
import sideproject.gugumo.validate.EnumValue;

import java.time.LocalDate;


@Getter
@Conditional.List(
        {
                @Conditional(
                        selected = "meetingType",
                        values = "SHORT",
                        required = "meetingDate"
                ),
                @Conditional(
                        selected = "meetingType",
                        values = "LONG",
                        required = "meetingDays"
                )
        }
)
public class UpdatePostReq {


    @NotEmpty
    @EnumValue(enumClass = MeetingType.class)
    @Schema(description = "모임의 단기, 장기 여부", implementation = MeetingType.class)
    private String meetingType;
    @NotEmpty
    @EnumValue(enumClass = GameType.class)
    @Schema(description = "모임 후 진행할 구기종목", implementation = GameType.class)
    private String gameType;
    @NotNull
    @Schema(description = "모임 인원")
    private int meetingMemberNum;
    @Schema(description = "모임 날짜")
    private LocalDate meetingDate;      //단기
    @NotNull
    @Schema(description = "모임 시각", minimum = "0", maximum = "23")
    private int meetingTime;
    @Schema(description = "모임 요일", example = "월;화;금;일;")
    private String meetingDays;         //장기
    @NotNull
    @Schema(description = "모집 제한 날짜")
    private LocalDate meetingDeadline;
    @NotEmpty
    @Schema(description = "오폰카톡방 주소")
    private String openKakao;
    @NotEmpty
    @Schema(description = "모임 지역", implementation = Location.class)
    private String location;

    @NotEmpty
    @Schema(description = "게시글 제목")
    private String title;
    @NotEmpty
    @Schema(description = "게시글 내용", maxLength = 10000)
    private String content;
    @NotEmpty
    @EnumValue(enumClass = MeetingStatus.class)
    @Schema(description = "모임 상태", allowableValues = {"RECRUIT, END"})
    private String meetingStatus;

}