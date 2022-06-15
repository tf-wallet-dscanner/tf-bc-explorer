package io.dkargo.bcexplorer.core.error;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorDTO {

    private int code;
    private String message;
    private String timestamp;

    public ErrorDTO(DkargoException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static ErrorDTO BAD_REQUEST_ERROR() {
        return new ErrorDTO(ErrorCodeEnum.INVALID_FORMAT.getCode(), ErrorCodeEnum.INVALID_FORMAT.getMessage(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public static ErrorDTO BAD_REQUEST_ERROR(String message) {
        return new ErrorDTO(ErrorCodeEnum.INVALID_FORMAT.getCode(), message, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public ResponseEntity<ErrorDTO> toResponse() {
        return ResponseEntity
                .status(ErrorCodeEnum.enumOf(this.code).getStatus())
                .body(this);
    }
}
