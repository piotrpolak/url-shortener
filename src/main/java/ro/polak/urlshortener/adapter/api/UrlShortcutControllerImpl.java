package ro.polak.urlshortener.adapter.api;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;

@RestController
@RequiredArgsConstructor
class UrlShortcutControllerImpl implements ApiApi {

  private final UrlShortcutFacadeService urlShortcutFacadeService;

  @Override
  public ResponseEntity<UrlShortcutResponseDto> createUrlShortcut(final Long X_AUTH_USER_ID,
                                                                  final @Valid UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcutResponseDto urlShortcutResponseDto = urlShortcutFacadeService.create(X_AUTH_USER_ID, urlShortcutRequestDto);
    return ResponseEntity.ok(urlShortcutResponseDto);
  }

  @Override
  public ResponseEntity<List<UrlShortcutResponseDto>> getUserShortcuts(Long userId) {
    return ResponseEntity.ok(urlShortcutFacadeService.getShortcutsByUserId(userId));
  }

  @Override
  public ResponseEntity<Void> deleteUrlShortcut(String urlShortcutId, Long X_AUTH_USER_ID) {
    urlShortcutFacadeService.deleteUrlShortcut(urlShortcutId, X_AUTH_USER_ID);
    return (ResponseEntity<Void>) ResponseEntity.accepted();
  }

  @Override
  public ResponseEntity<UrlShortcutResponseDto> updateUrlShortcut(String urlShortcutId, Long X_AUTH_USER_ID, @Valid UrlShortcutRequestDto urlShortcutRequestDto) {
    return ResponseEntity.ok(urlShortcutFacadeService.updateUrlShortcut(urlShortcutId, X_AUTH_USER_ID, urlShortcutRequestDto));
  }
}
