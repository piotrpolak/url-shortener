package ro.polak.urlshortener.domain.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

  public abstract HttpStatus getStatus();

  public abstract String getSituationMessage();
}
