package sideproject.gugumo.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sideproject.gugumo.api.swaggerapi.MailApi;
import sideproject.gugumo.domain.dto.emailDto.EmailCheckDto;
import sideproject.gugumo.domain.dto.emailDto.EmailRequestDto;
import sideproject.gugumo.response.ApiResponse;
import sideproject.gugumo.service.MailSenderService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mail")
public class MailController implements MailApi {

    private final MailSenderService mailService;

    @PostMapping("/api/v1/mailSend")
    @Operation(summary = "이메일 인증 번호 전송", description = "회원가입 시 이메일 인증번호를 전송합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "인증번호 전송 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"success\", \"data\" : \"인증번호 전송 완료\", \"message\" : null}"
                                    )))
            }
    )
    public ApiResponse<String> mailSend(@RequestBody @Valid EmailRequestDto emailDto) {

        mailService.joinEmail(emailDto.getEmail());

        return ApiResponse.createSuccess("인증번호 전송 완료.");
    }

    @PostMapping("/api/v1/mailAuthCheck")
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
    public ApiResponse<String> AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {

        mailService.checkAuthNum(emailCheckDto.getEmail(), emailCheckDto.getEmailAuthNum());

        return ApiResponse.createSuccess("인증 완료.");
    }
}
