package ro.polak.urlshortener;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.polak.urlshortener.support.DocumentationAndValidationMockMvcBuilderCustomizer;

@Configuration
public class AdditionalTestConfiguration {

  @Bean
  MockMvcBuilderCustomizer mockMvcBuilderCustomizer() {
    return new DocumentationAndValidationMockMvcBuilderCustomizer();
  }
}
