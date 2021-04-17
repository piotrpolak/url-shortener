package ro.polak.urlshortener.adapter.api;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.domain.model.UrlShortcut;

@Service
class UrlShortcutApiMapper {

  @SneakyThrows
  public UrlShortcut toUrlShortcut(final long userId, final UrlShortcutRequestDto urlShortcutRequestDto) {
    return UrlShortcut.builder()
        .createdBy(userId)
        .destinationUrl(new URI(urlShortcutRequestDto.getDestinationUrl()))
        .build();
  }

  public UrlShortcutResponseDto toUrlShortcutResponse(final UrlShortcut urlShortcut) {
    return UrlShortcutResponseDto.builder()
        .destinationUrl(urlShortcut.getDestinationUrl().toString())
        .createdAt(urlShortcut.getCreatedAt())
        .id(urlShortcut.getTextId())
        .build();
  }

  public List<UrlShortcutResponseDto> toUrlShortcutsResponse(List<UrlShortcut> urlShortcuts) {
    return urlShortcuts.stream()
        .map(urlShortcut -> UrlShortcutResponseDto.builder()
            .destinationUrl(urlShortcut.getDestinationUrl().toString())
            .id(urlShortcut.getTextId())
            .createdAt(urlShortcut.getCreatedAt())
            .build())
        .collect(Collectors.toList());
  }
}
