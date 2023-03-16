package ro.polak.urlshortener.adapter.api;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.domain.service.UrlShortcutFacade;

@RestController
@RequiredArgsConstructor
class UrlShortcutControllerImpl implements ShortcutApi {

  private final UrlShortcutFacade urlShortcutFacade;

  @Override
  public ResponseEntity<UrlShortcutResponseDto> createUrlShortcut(
      final Long userIdFromSecurityContext, final UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcutResponseDto urlShortcutResponseDto =
        urlShortcutFacade.create(userIdFromSecurityContext, urlShortcutRequestDto);
    return ResponseEntity.created(URI.create(urlShortcutResponseDto.getDestinationUrl()))
        .body(urlShortcutResponseDto);
  }

  @Override
  public ResponseEntity<List<UrlShortcutResponseDto>> getUserShortcuts(Long userId) {
    return ResponseEntity.ok(urlShortcutFacade.getShortcutsByUserId(userId));
  }

  @Override
  public ResponseEntity<Void> deleteUrlShortcut(
      String urlShortcutId, Long userIdFromSecurityContext) {
    urlShortcutFacade.deleteUrlShortcut(urlShortcutId, userIdFromSecurityContext);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<UrlShortcutResponseDto> getUrlShortcut(
      String urlShortcutId, Long userIdFromSecurityContext) {
    return ResponseEntity.ok(
        urlShortcutFacade.getShortcutsByIdAndUserId(urlShortcutId, userIdFromSecurityContext));
  }

  @Override
  public ResponseEntity<UrlShortcutResponseDto> updateUrlShortcut(
      String urlShortcutId,
      Long userIdFromSecurityContext,
      UrlShortcutRequestDto urlShortcutRequestDto) {
    return ResponseEntity.ok(
        urlShortcutFacade.updateUrlShortcut(
            urlShortcutId, userIdFromSecurityContext, urlShortcutRequestDto));
  }
}
