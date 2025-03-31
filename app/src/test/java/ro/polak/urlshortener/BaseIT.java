package ro.polak.urlshortener;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import ro.polak.urlshortener.domain.repository.UrlShortcutRepository;
import ro.polak.urlshortener.support.DocumentationAndValidationMockMvcBuilderCustomizer;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("itest")
@Import(BaseIT.AdditionalTestConfiguration.class)
public abstract class BaseIT {

  static PostgreSQLContainer<?> postgresql =
      new PostgreSQLContainer<>("postgres:15.5")
          .withDatabaseName("url_shortcuts")
          .withUsername("url_shortcuts")
          .withPassword("url_shortcuts");

  static {
    postgresql.start();
  }

  @DynamicPropertySource
  static void configurePostgresProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgresql::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgresql::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgresql::getPassword);
  }

  @Autowired protected MockMvc mockMvc;

  /**
   * The repository is intentionally injected as private so that the actual tests implementation
   * tests the logic behavior rather than the database details.
   */
  @Autowired private UrlShortcutRepository urlShortcutRepository;

  @AfterEach
  void cleanUpDatabase() {
    urlShortcutRepository.deleteAll();
  }

  @Configuration
  static class AdditionalTestConfiguration {

    @Bean
    MockMvcBuilderCustomizer mockMvcBuilderCustomizer() {
      return new DocumentationAndValidationMockMvcBuilderCustomizer();
    }
  }
}
