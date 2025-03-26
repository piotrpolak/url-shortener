package ro.polak.urlshortener.domain.service;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutRequestDto;
import ro.polak.urlshortener.adapter.api.dto.UrlShortcutResponseDto;
import ro.polak.urlshortener.domain.model.UrlShortcut;

@Mapper(componentModel = "spring")
interface UrlShortcutApiMapper {

  @Mapping(target = "createdAt", ignore = true) // Populated by Hibernate
  @Mapping(target = "textId", ignore = true) // Populated by Hibernate
  UrlShortcut toUrlShortcut(
      final long createdBy, final UrlShortcutRequestDto urlShortcutRequestDto);

  @Mapping(target = "id", source = "textId")
  @Mapping(target = "shortenedUrl", source = ".")
  UrlShortcutResponseDto toUrlShortcutResponse(final UrlShortcut urlShortcut);

  default String toShortenedUrl(UrlShortcut urlShortcut) {
    return "http://localhost:8080/" + urlShortcut.getTextId();
  }

  List<UrlShortcutResponseDto> toUrlShortcutsResponse(List<UrlShortcut> urlShortcuts);
}
