package sideproject.gugumo.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }
    public static <T> ApiResponse<T> createSuccess() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }
    public static <T> ApiResponse<T> createFail(T data, String failMessage) {
        return new ApiResponse<>(FAIL_STATUS, data, failMessage);
    }
    public static <T> ApiResponse<T> createFail(String failMessage) {
        return new ApiResponse<>(FAIL_STATUS, null, failMessage);
    }
}