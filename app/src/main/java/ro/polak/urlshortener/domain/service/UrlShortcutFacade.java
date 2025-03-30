package ro.polak.urlshortener.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.polak.urlshortener.domain.model.UrlShortcut;
import ro.polak.urlshortener.generated.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.generated.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.mapper.UrlShortcutApiMapper;

@Service
@RequiredArgsConstructor
public class UrlShortcutFacade {

  private final UrlShortcutService urlShortcutService;
  private final UrlShortcutApiMapper urlShortcutApiMapper;

  public UrlShortcutResponseDto create(
      final long userId, final UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcut urlShortcut =
        urlShortcutApiMapper.toUrlShortcut(userId, urlShortcutRequestDto);
    final UrlShortcut createdUrlShortcut = urlShortcutService.create(urlShortcut);
    return urlShortcutApiMapper.toUrlShortcutResponse(createdUrlShortcut);
  }

  public List<UrlShortcutResponseDto> getShortcutsByUserId(long userId) {
    final List<UrlShortcut> urlShortcuts = urlShortcutService.getUrlShortcutsByUserId(userId);
    return urlShortcutApiMapper.toUrlShortcutsResponse(urlShortcuts);
  }

  public UrlShortcutResponseDto getShortcutsByIdAndUserId(String urlShortcutId, Long userId) {
    UrlShortcut urlShortcut = urlShortcutService.getShortcutsByIdAndUserId(urlShortcutId, userId);
    return urlShortcutApiMapper.toUrlShortcutResponse(urlShortcut);
  }

  public UrlShortcutResponseDto updateUrlShortcut(
      String urlShortcutId, long userId, UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcut urlShortcut =
        urlShortcutApiMapper.toUrlShortcut(userId, urlShortcutRequestDto);
    final UrlShortcut updatedUrlShortcut = urlShortcutService.update(urlShortcutId, urlShortcut);
    return urlShortcutApiMapper.toUrlShortcutResponse(updatedUrlShortcut);
  }

  public void deleteUrlShortcut(String urlShortcutId, long userId) {
    urlShortcutService.deleteUrlShortcut(urlShortcutId, userId);
  }

  public String getRedirectUrlByShortcutId(String urlShortcutId) {
    final UrlShortcut urlShortcut = urlShortcutService.getShortcutById(urlShortcutId);
    return urlShortcutApiMapper.toUrlShortcutResponse(urlShortcut).getDestinationUrl();
  }
}
