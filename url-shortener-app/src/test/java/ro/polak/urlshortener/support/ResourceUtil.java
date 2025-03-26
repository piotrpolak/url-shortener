package ro.polak.urlshortener.support;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

@UtilityClass
public class ResourceUtil {

  public static String getResourceAsString(String path) {
    try {
      return new ClassPathResource(path).getContentAsString(StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
