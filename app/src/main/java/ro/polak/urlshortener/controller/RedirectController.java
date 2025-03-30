package ro.polak.urlshortener.controller;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.polak.urlshortener.domain.service.UrlShortcutFacade;

/** Helper controller to simulate the actual redirect */
@RestController
@RequiredArgsConstructor
public class RedirectController {

  private final UrlShortcutFacade urlShortcutFacade;

  @GetMapping(value = "{urlShortcutId:[a-zA-Z0-9]+}")
  ResponseEntity<?> redirect(@PathVariable String urlShortcutId) {
    return ResponseEntity.status(PERMANENT_REDIRECT)
        .header(LOCATION, urlShortcutFacade.getRedirectUrlByShortcutId(urlShortcutId))
        .build();
  }
}
