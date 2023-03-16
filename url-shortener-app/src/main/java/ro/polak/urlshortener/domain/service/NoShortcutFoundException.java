package ro.polak.urlshortener.domain.service;

import org.springframework.http.HttpStatus;

public class NoShortcutFoundException extends BusinessException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  public String getSituationMessage() {
    return "Item not found";
  }
}
