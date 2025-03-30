package ro.polak.urlshortener.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.polak.urlshortener.support.DocumentationAndValidationMockMvcBuilderCustomizer.SKIP_OPEN_API_VALIDATION_ATTRIBUTE;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ro.polak.urlshortener.BaseIT;

class StaticResourcesControllerITest extends BaseIT {

  @ParameterizedTest
  @CsvSource({"/url-shortener.yaml"})
  public void should_serve_static_content(String uri) throws Exception {
    mockMvc
        .perform(get(uri).requestAttr(SKIP_OPEN_API_VALIDATION_ATTRIBUTE, true))
        .andExpect(status().isOk());
  }
}
