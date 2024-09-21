package sideproject.gugumo.api.swaggerapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sideproject.gugumo.domain.dto.emailDto.EmailCheckDto;
import sideproject.gugumo.domain.dto.emailDto.EmailRequestDto;
import sideproject.gugumo.response.ApiResponse;

public interface MailApi {

    @Operation(summary = "이메일 인증 번호 전송", description = "회원가입 시 이메일 인증번호를 전송합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증번호 전송 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"인증번호 전송 완료\", \"message\" : null}"
                                    )))
            }
    )
    public ApiResponse<String> mailSend(@RequestBody @Valid EmailRequestDto emailDto);

    @Operation(summary = "이메일 인증번호 검증", description = "회원가입 시 이메일 인증번호를 검증합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증번호 검증 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"인증 완료\", \"message\" : null}"
                                    ))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "인증번호 검증 실패",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"인증 실패.\"}"
                                    )))
            })
    public ApiResponse<String> AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto);
}
