package ro.polak.urlshortener.adapter.api;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import ro.polak.urlshortener.BaseIT;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UrlShortcutControllerImplTest extends BaseIT {

  public static final String X_AUTH_USER_ID_HEADER = "X-AUTH-USER-ID";
  public static final int OTHWER_USER_ID = 999;

  @Test
  void should_create_new_url_shortcut() throws Exception {
    mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}")
    ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.createdAt").exists())
        .andExpect(jsonPath("$.destinationUrl", equalTo("https://www.google.com/")));
  }

  @Test
  void should_list_user_shortcuts() throws Exception {
    mockMvc.perform(get("/api/v1/users/123/shortcuts")
        .header(X_AUTH_USER_ID_HEADER, 123)
    ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", equalTo(0)));

    mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}")
    ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.createdAt").exists())
        .andExpect(jsonPath("$.destinationUrl", equalTo("https://www.google.com/")));


    mockMvc.perform(get("/api/v1/users/123/shortcuts")
        .header(X_AUTH_USER_ID_HEADER, 123)
    ).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.length()", equalTo(1)));
  }

  @Test
  void should_not_create_url_shortcut_for_missing_user_id_header() throws Exception {
    mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  void should_not_create_url_shortcut_for_missing_destination_id() throws Exception {
    mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{}")
    ).andExpect(status().isBadRequest());
  }

  @Test
  void should_delete_shortcut()  throws Exception {
    MvcResult result = mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}"))
        .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(delete("/api/v1/shortcuts/"+id)
        .header(X_AUTH_USER_ID_HEADER, 123)
    ).andExpect(status().isAccepted());

    // TODO Make sure the shortcut was actually deleted
  }

  @Test
  void should_not_be_able_to_delete_non_existing_shortcuts()  throws Exception {
    mockMvc.perform(delete("/api/v1/shortcuts/XYZ")
        .header(X_AUTH_USER_ID_HEADER, 123)
    ).andExpect(status().isNotFound());
  }

  @Test
  void should_not_allow_deleting_shortcuts_of_another_user()  throws Exception {
    MvcResult result = mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}"))
        .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(delete("/api/v1/shortcuts/"+id)
        .header(X_AUTH_USER_ID_HEADER, OTHWER_USER_ID)
    ).andExpect(status().isForbidden());

    // TODO Make sure the shortcut is still there
  }

  @Test
  void should_not_be_able_to_update_non_existing_shortcuts()  throws Exception {
    mockMvc.perform(post("/api/v1/shortcuts/XYZ")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}")
    ).andExpect(status().isNotFound());
  }

  @Test
  void should_update_shortcut()  throws Exception {
    MvcResult result = mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}"))
        .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(post("/api/v1/shortcuts/"+id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.example.com/\"}")
    ).andExpect(status().isOk());

    // TODO Make sure the shortcut was actually updated
  }

  @Test
  void should_not_allow_updating_shortcuts_of_another_user()  throws Exception {
    MvcResult result = mockMvc.perform(post("/api/v1/shortcuts")
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, 123)
        .content("{\"destinationUrl\": \"https://www.google.com/\"}"))
        .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc.perform(post("/api/v1/shortcuts/"+id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(X_AUTH_USER_ID_HEADER, OTHWER_USER_ID)
        .content("{\"destinationUrl\": \"https://www.example.com/\"}")
    ).andExpect(status().isForbidden());

    // TODO Make sure the shortcut was actually updated
  }
}