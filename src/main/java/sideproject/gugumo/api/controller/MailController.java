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
    public ApiResponse<String> mailSend(@RequestBody @Valid EmailRequestDto emailDto) {

        mailService.joinEmail(emailDto.getEmail());

        return ApiResponse.createSuccess("인증번호 전송 완료.");
    }

    @PostMapping("/api/v1/mailAuthCheck")
    public ApiResponse<String> AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {

        mailService.checkAuthNum(emailCheckDto.getEmail(), emailCheckDto.getEmailAuthNum());

        return ApiResponse.createSuccess("인증 완료.");
    }
}
