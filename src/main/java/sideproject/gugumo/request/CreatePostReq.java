package sideproject.gugumo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import sideproject.gugumo.domain.entity.meeting.GameType;
import sideproject.gugumo.domain.entity.meeting.Location;
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
public class CreatePostReq {


    @NotEmpty
    @EnumValue(enumClass = MeetingType.class)
    @Schema(description = "모임의 단기, 장기 여부", implementation = MeetingType.class)
    private String meetingType;     //단기, 장기
    @NotEmpty
    @EnumValue(enumClass = GameType.class)
    @Schema(description = "모임 후 진행할 구기종목", implementation = GameType.class)
    private String gameType;
    @NotNull
    @Schema(description = "모임 인원")
    private int meetingMemberNum;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    @Schema(description = "모임 날짜")
    private LocalDate meetingDate;      //날짜: 단기일 경우
    @Schema(description = "모임 요일", example = "월;화;금;일;")
    private String meetingDays;     //요일: 장기일 경우
    @NotNull
    @Schema(description = "모임 시각", minimum = "0", maximum = "23")
    private int meetingTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @FutureOrPresent
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

}
