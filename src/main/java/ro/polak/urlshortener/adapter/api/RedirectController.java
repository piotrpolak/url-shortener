package ro.polak.urlshortener.adapter.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ro.polak.urlshortener.domain.service.UrlShortcutFacadeService;

import static org.springframework.http.HttpHeaders.LOCATION;

/**
 * Helper controller to simulate the actual redirect
 */
@RestController
@RequiredArgsConstructor
public class RedirectController {

  private final UrlShortcutFacadeService urlShortcutFacadeService;

  @GetMapping(value = "{urlShortcutId:[a-zA-Z0-9]+}")
  ResponseEntity redirect(@PathVariable String urlShortcutId) {
    return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
        .header(LOCATION, urlShortcutFacadeService.getRedirectUrlByShortcutId(urlShortcutId)).build();
  }
}
