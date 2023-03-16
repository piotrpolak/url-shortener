package ro.polak.urlshortener.adapter.api;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.polak.urlshortener.support.DocumentationAndValidationMockMvcBuilderCustomizer.SKIP_OPEN_API_VALIDATION_ATTRIBUTE;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ro.polak.urlshortener.BaseIT;

class RedirectControllerITest extends BaseIT {

  private static final String X_AUTH_USER_ID_HEADER = "X-AUTH-USER-ID";

  private static final int DEFAULT_USER_ID = 123;

  @Test
  public void should_create_shortcut_and_do_redirect() throws Exception {
    var result =
        mockMvc
            .perform(
                post("/api/v1/shortcuts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(X_AUTH_USER_ID_HEADER, DEFAULT_USER_ID)
                    .content("{\"destinationUrl\": \"https://www.google.com/\"}"))
            .andExpect(status().isCreated())
            .andReturn();

    var id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc
        .perform(get("/" + id).requestAttr(SKIP_OPEN_API_VALIDATION_ATTRIBUTE, true))
        .andExpect(status().isPermanentRedirect())
        .andExpect(header().string("Location", equalTo("https://www.google.com/")));
  }
}
