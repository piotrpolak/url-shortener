package ro.polak.urlshortener.domain.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.polak.urlshortener.domain.model.UrlShortcut;

@Repository
public interface UrlShortcutRepository extends CrudRepository<UrlShortcut, String> {

  List<UrlShortcut> getUrlShortcutsByCreatedBy(long createdBy);
}
