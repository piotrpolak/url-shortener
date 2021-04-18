package ro.polak.urlshortener.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.polak.urlshortener.domain.model.UrlShortcut;
import ro.polak.urlshortener.domain.repository.UrlShortcutRepository;

@RequiredArgsConstructor
@Service
class UrlShortcutService {

  private final UrlShortcutRepository urlShortcutRepository;

  UrlShortcut create(UrlShortcut urlShortcut) {
    return urlShortcutRepository.save(urlShortcut);
  }

  List<UrlShortcut> getUrlShortcutsByUserId(long userId) {
    return urlShortcutRepository.getUrlShortcutsByCreatedBy(userId);
  }

  UrlShortcut getShortcutsByIdAndUserId(String urlShortcutId, Long userId) {
    UrlShortcut existingUrlShortcut = getShortcutById(urlShortcutId);

    if (existingUrlShortcut.getCreatedBy() != userId) {
      throw new UserNotEntitledToShortcutException();
    }

    return existingUrlShortcut;
  }

  void deleteUrlShortcut(String urlShortcutId, long userId) {
    UrlShortcut existingUrlShortcut = getShortcutById(urlShortcutId);

    if (existingUrlShortcut.getCreatedBy() != userId) {
      throw new UserNotEntitledToShortcutException();
    }

    urlShortcutRepository.delete(existingUrlShortcut);
  }

  UrlShortcut update(String urlShortcutId, UrlShortcut urlShortcut) {
    UrlShortcut existingUrlShortcut = getShortcutById(urlShortcutId);

    if (existingUrlShortcut.getCreatedBy() != urlShortcut.getCreatedBy()) {
      throw new UserNotEntitledToShortcutException();
    }

    existingUrlShortcut.setDestinationUrl(urlShortcut.getDestinationUrl());
    return urlShortcutRepository.save(existingUrlShortcut);
  }

  UrlShortcut getShortcutById(String urlShortcutId) {
    return urlShortcutRepository.findById(urlShortcutId).orElseThrow(NoShortcutFoundException::new);
  }
}
