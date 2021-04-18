package ro.polak.urlshortener.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
class UserNotEntitledToShortcutException extends RuntimeException {
}
