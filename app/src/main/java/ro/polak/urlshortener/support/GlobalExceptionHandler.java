package ro.polak.urlshortener.support;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.polak.urlshortener.domain.exception.BusinessException;
import ro.polak.urlshortener.generated.api.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleBusinessException(BusinessException ex) {
    var errorDto =
        ErrorDto.builder()
            .code(Long.valueOf(ex.getStatus().value()))
            .message(ex.getSituationMessage())
            .build();

    return ResponseEntity.status(ex.getStatus()).body(errorDto);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorDto> handleConstraintViolationException(
      ConstraintViolationException ex) {
    var errorDto =
        ErrorDto.builder()
            .code(Long.valueOf(HttpStatus.BAD_REQUEST.value()))
            .message("Bad request. Validation error.")
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
  }
}
