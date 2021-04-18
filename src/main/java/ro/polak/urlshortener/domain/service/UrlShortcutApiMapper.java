package ro.polak.urlshortener.domain.service;

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
  UrlShortcut toUrlShortcut(final long userId, final UrlShortcutRequestDto urlShortcutRequestDto) {
    return UrlShortcut.builder()
        .createdBy(userId)
        .destinationUrl(new URI(urlShortcutRequestDto.getDestinationUrl()))
        .build();
  }

  UrlShortcutResponseDto toUrlShortcutResponse(final UrlShortcut urlShortcut) {
    return UrlShortcutResponseDto.builder()
        .destinationUrl(urlShortcut.getDestinationUrl().toString())
        .createdAt(urlShortcut.getCreatedAt())
        .createdBy(urlShortcut.getCreatedBy())
        .shortenedUrl("http://localhost:8080/" + urlShortcut.getTextId())
        .id(urlShortcut.getTextId())
        .build();
  }

  List<UrlShortcutResponseDto> toUrlShortcutsResponse(List<UrlShortcut> urlShortcuts) {
    return urlShortcuts.stream()
        .map(urlShortcut -> toUrlShortcutResponse(urlShortcut))
        .collect(Collectors.toList());
  }
}
