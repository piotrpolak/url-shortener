package ro.polak.urlshortener.support;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static java.lang.Boolean.TRUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

public class DocumentationAndValidationMockMvcBuilderCustomizer
    implements MockMvcBuilderCustomizer {

  public static final String SKIP_OPEN_API_VALIDATION_ATTRIBUTE =
      "SKIP_OPEN_API_VALIDATION_ATTRIBUTE";

  private static final ResultMatcher openApiResultMatcher;

  static {
    var openApiSpec = ResourceUtil.getResourceAsString("static/url-shortener.yaml");
    var validator =
        OpenApiInteractionValidator.createForInlineApiSpecification(openApiSpec).build();

    openApiResultMatcher = openApi().isValid(validator);
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
              if (shouldValidateAgainstOpenApi(result)) {
                openApiResultMatcher.match(result);
              }
            });
  }

  private static boolean shouldValidateAgainstOpenApi(MvcResult result) {
    return TRUE != result.getRequest().getAttribute(SKIP_OPEN_API_VALIDATION_ATTRIBUTE);
  }
}
