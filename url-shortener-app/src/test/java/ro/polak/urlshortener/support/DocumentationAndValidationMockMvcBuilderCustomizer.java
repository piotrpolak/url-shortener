package ro.polak.urlshortener.support;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

public class DocumentationAndValidationMockMvcBuilderCustomizer
    implements MockMvcBuilderCustomizer {

  public static final String SKIP_OPEN_API_VALIDATION_ATTRIBUTE =
      "SKIP_OPEN_API_VALIDATION_ATTRIBUTE";

  private static final ResultMatcher openApiResultMatcher;

  static {
    try {
      var validator =
          OpenApiInteractionValidator.createForInlineApiSpecification(
                  new String(
                      Files.readAllBytes(
                          new ClassPathResource("static/url-shortener.yaml").getFile().toPath())))
              .build();

      openApiResultMatcher = openApi().isValid(validator);

    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public void customize(ConfigurableMockMvcBuilder<?> builder) {
    builder
        .alwaysDo(
            document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())))
        .alwaysExpect(
            (result) -> {
              if (Boolean.TRUE
                  != result.getRequest().getAttribute(SKIP_OPEN_API_VALIDATION_ATTRIBUTE)) {
                openApiResultMatcher.match(result);
              }
            });
  }
}
