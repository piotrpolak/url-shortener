package ro.polak.urlshortener.support;

import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcBuilderCustomizer;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;

public class DocumentingMockMvcBuilderCustomizer implements MockMvcBuilderCustomizer {

  @Override
  public void customize(ConfigurableMockMvcBuilder<?> builder) {
    builder.alwaysDo(
        MockMvcRestDocumentation.document(
            "{class-name}/{method-name}",
            Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
            Preprocessors.preprocessResponse(Preprocessors.prettyPrint())));
  }
}
