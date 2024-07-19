package sideproject.gugumo.domain.dto.detailpostdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@SuperBuilder
public class LongDetailPostDto extends DetailPostDto{

    @Schema(description = "모임 시각", minimum = "0", maximum = "23")
    private LocalTime meetingTime;
    @Schema(description = "모임 요일", example = "월;화;금;일;")
    private String meetingDays;
}
