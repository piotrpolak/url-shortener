package ro.polak.urlshortener.adapter.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.domain.model.UrlShortcut;
import ro.polak.urlshortener.domain.service.UrlShortcutService;

@Service
@RequiredArgsConstructor
class UrlShortcutFacadeService {

  private final UrlShortcutService urlShortcutService;
  private final UrlShortcutApiMapper urlShortcutApiMapper;

  public UrlShortcutResponseDto create(final long userId, final UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcut urlShortcut = urlShortcutApiMapper.toUrlShortcut(userId, urlShortcutRequestDto);
    final UrlShortcut createdUrlShortcut = urlShortcutService.create(urlShortcut);
    return urlShortcutApiMapper.toUrlShortcutResponse(createdUrlShortcut);
  }

  public List<UrlShortcutResponseDto> getShortcutsByUserId(long userId) {
    final List<UrlShortcut> urlShortcuts = urlShortcutService.getUrlShortcutsByUserId(userId);
    return urlShortcutApiMapper.toUrlShortcutsResponse(urlShortcuts);
  }

  public UrlShortcutResponseDto updateUrlShortcut(String urlShortcutId, long userId, UrlShortcutRequestDto urlShortcutRequestDto) {
    final UrlShortcut urlShortcut = urlShortcutApiMapper.toUrlShortcut(userId, urlShortcutRequestDto);
    final UrlShortcut updatedUrlShortcut = urlShortcutService.update(urlShortcutId, urlShortcut);
    return urlShortcutApiMapper.toUrlShortcutResponse(updatedUrlShortcut);
  }

  public void deleteUrlShortcut(String urlShortcutId, long userId) {
    urlShortcutService.deleteUrlShortcut(urlShortcutId, userId);
  }
}
