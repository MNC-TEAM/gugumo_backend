package sideproject.gugumo.api.swaggerapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sideproject.gugumo.domain.dto.memberDto.KakaoLoginRequestDto;
import sideproject.gugumo.domain.dto.memberDto.SignUpKakaoMemberDto;
import sideproject.gugumo.response.ApiResponse;

public interface KakaoApi {

    @Operation(summary = "카카오 로그인", description = "카카오 계정으로 로그인합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "해당 사용자에 대한 access token",
                            content = @Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject("{\n" +
                                            "    \"status\": \"success\",\n" +
                                            "    \"data\": \"Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJ1c2VybmFtZSIsInJvbGUiOiJST0xFX1VTRVIiLCJpYXQiOjE3MTkyNDExMzEsImV4cCI6MTcxOTMyNzUzMX0.QRiTv8Z-Bv5knTMLLxTvmpo6Sz_0k7TfzRtSpEmUSvw\",\n" +
                                            "    \"message\": null\n" +
                                            "}"))),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "회원이 없음",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\"status\" : \"fail\", \"data\" : null, \"message\" : \"회원이 없습니다.\"}"
                                    )))
            })
    public ApiResponse<String> login(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto);


    @Operation(summary = "카카오 회원가입", description = "카카오 계정으로 회원가입을 진행합니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "카카오 회원가입 완료",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n" +
                                                    "    \"status\": \"success\",\n" +
                                                    "    \"data\": true,\n" +
                                                    "    \"message\": null\n" +
                                                    "}"
                                    )))
            })
    public ApiResponse<Boolean> join(@RequestBody SignUpKakaoMemberDto signUpKakaoMemberDto);
}
