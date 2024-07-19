package sideproject.gugumo.domain.dto.simplepostdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@AllArgsConstructor
public class SimplePostShortDto extends SimplePostDto {

    @Schema(description = "모임 날짜 및 시각")
    private LocalDateTime meetingDateTime;
}
