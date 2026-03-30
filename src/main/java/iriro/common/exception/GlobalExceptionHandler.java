package iriro.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역 예외 처리기
@Slf4j // 로그 객체를 자동으로 만들어 줌.
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<String> handleExternalApiException(ExternalApiException e) {

        log.error("외부 API 호출 실패: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("경로 조회에 실패했습니다.");
    }
}
