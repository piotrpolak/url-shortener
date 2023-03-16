package ro.polak.urlshortener.domain.service;

import org.springframework.http.HttpStatus;

class UserNotEntitledToShortcutException extends BusinessException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }

  @Override
  public String getSituationMessage() {
    return "No access";
  }
}
