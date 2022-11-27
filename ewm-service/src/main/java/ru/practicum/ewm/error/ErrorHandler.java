package ru.practicum.ewm.error;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ApiError {
        private StackTraceElement[] errors;
        private String message;
        private String reason;
        private HttpStatus status;
        private String timestamp;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleConflictException(ConflictException e) {
        log.error(e.getMessage(), e);
        ApiError apiError = ApiError.builder()
                .errors(e.getStackTrace())
                .message(e.getMessage())
                .reason("Unique index or primary key violation")
                .status(HttpStatus.CONFLICT)
                .timestamp(getTimeStamp()).build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

     private String getTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
     }
}