package ro.polak.urlshortener.adapter.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.domain.service.UrlShortcutFacadeService;

@RestController
@RequiredArgsConstructor
class UrlShortcutControllerImpl implements ShortcutApi {

  private final UrlShortcutFacadeService urlShortcutFacadeService;

  @Override
  public ResponseEntity<UrlShortcutResponseDto> createUrlShortcut(
      final Long userIdFromSecurityContext, final UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcutResponseDto urlShortcutResponseDto =
        urlShortcutFacadeService.create(userIdFromSecurityContext, urlShortcutRequestDto);
    return ResponseEntity.ok(urlShortcutResponseDto);
  }

  @Override
  public ResponseEntity<List<UrlShortcutResponseDto>> getUserShortcuts(Long userId) {
    return ResponseEntity.ok(urlShortcutFacadeService.getShortcutsByUserId(userId));
  }

  @Override
  public ResponseEntity<Void> deleteUrlShortcut(
      String urlShortcutId, Long userIdFromSecurityContext) {
    urlShortcutFacadeService.deleteUrlShortcut(urlShortcutId, userIdFromSecurityContext);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<UrlShortcutResponseDto> getUrlShortcut(
      String urlShortcutId, Long userIdFromSecurityContext) {
    return ResponseEntity.ok(
        urlShortcutFacadeService.getShortcutsByIdAndUserId(
            urlShortcutId, userIdFromSecurityContext));
  }

  @Override
  public ResponseEntity<UrlShortcutResponseDto> updateUrlShortcut(
      String urlShortcutId,
      Long userIdFromSecurityContext,
      UrlShortcutRequestDto urlShortcutRequestDto) {
    return ResponseEntity.ok(
        urlShortcutFacadeService.updateUrlShortcut(
            urlShortcutId, userIdFromSecurityContext, urlShortcutRequestDto));
  }
}
