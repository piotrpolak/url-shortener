package ro.polak.urlshortener.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.polak.urlshortener.domain.model.UrlShortcut;
import ro.polak.urlshortener.domain.repository.UrlShortcutRepository;

@RequiredArgsConstructor
@Service
public class UrlShortcutService {

  private final UrlShortcutRepository urlShortcutRepository;

  public UrlShortcut create(UrlShortcut urlShortcut) {
    return urlShortcutRepository.save(urlShortcut);
  }

  public List<UrlShortcut> getUrlShortcutsByUserId(long userId) {
    return urlShortcutRepository.getUrlShortcutsByCreatedBy(userId);
  }

  public void deleteUrlShortcut(String urlShortcutId, long userId) {
    UrlShortcut existingUrlShortcut = urlShortcutRepository.findById(urlShortcutId).orElseThrow(NoShortcutFoundException::new);

    if(existingUrlShortcut.getCreatedBy() != userId) {
      throw new UserNotEntitledToShortcutException();
    }

    urlShortcutRepository.delete(existingUrlShortcut);
  }

  public UrlShortcut update(String urlShortcutId, UrlShortcut urlShortcut) {
    UrlShortcut existingUrlShortcut = urlShortcutRepository.findById(urlShortcutId).orElseThrow(NoShortcutFoundException::new);

    if(existingUrlShortcut.getCreatedBy() != urlShortcut.getCreatedBy()) {
      throw new UserNotEntitledToShortcutException();
    }

    existingUrlShortcut.setDestinationUrl(urlShortcut.getDestinationUrl());
    return urlShortcutRepository.save(existingUrlShortcut);
  }
}
