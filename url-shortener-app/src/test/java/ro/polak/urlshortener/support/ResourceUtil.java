package ro.polak.urlshortener.support;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

@UtilityClass
public class ResourceUtil {

  public static String getResourceAsString(String path) {
    try {
      var resourcePath = new ClassPathResource(path).getFile().toPath();
      return new String(Files.readAllBytes(resourcePath));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
