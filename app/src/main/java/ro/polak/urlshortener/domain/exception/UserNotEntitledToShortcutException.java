package ro.polak.urlshortener.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotEntitledToShortcutException extends BusinessException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }

  @Override
  public String getSituationMessage() {
    return "No access";
  }
}
