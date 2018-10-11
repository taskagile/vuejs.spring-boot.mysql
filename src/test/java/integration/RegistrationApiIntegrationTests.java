package integration;

import com.taskagile.TaskAgileApplication;
import com.taskagile.utils.JsonUtils;
import com.taskagile.web.payload.RegistrationPayload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TaskAgileApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class RegistrationApiIntegrationTests {

  @Autowired
  private MockMvc mvcMock;

  private RegistrationPayload payload(String username, String emailAddress) {
    RegistrationPayload payload = new RegistrationPayload();
    payload.setUsername(username);
    payload.setEmailAddress(emailAddress);
    payload.setPassword("MyPassword!");
    payload.setFirstName("User");
    payload.setLastName("Test");
    return payload;
  }

  @Test
  public void register_blankPayload_shouldFailAndReturn400() throws Exception {
    mvcMock.perform(post("/api/registrations"))
      .andExpect(status().is(400));
  }

  @Test
  public void register_validPayload_shouldSucceedAndReturn201() throws Exception {
    RegistrationPayload payload = payload("sunny", "sunny@taskagile.com");
    mvcMock.perform(
      post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(201));
  }

  @Test
  public void register_existedUsername_shouldFailAndReturn400() throws Exception {
    RegistrationPayload payload = payload("exist", "test1@taskagile.com");
    mvcMock.perform(
      post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(201));
    // Try to register again with the same username
    RegistrationPayload payload2 = payload("exist", "test2@taskagile.com");
    mvcMock.perform(
      post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload2)))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.message").value("Username already exists"));
  }

  @Test
  public void register_existedEmailAddress_shouldFailAndReturn400() throws Exception {
    RegistrationPayload payload = payload("test1", "exist@taskagile.com");
    mvcMock.perform(
      post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload)))
      .andExpect(status().is(201));
    // Try to register with the same email address
    RegistrationPayload payload2 = payload("test2", "exist@taskagile.com");
    mvcMock.perform(
      post("/api/registrations")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtils.toJson(payload2)))
      .andExpect(status().is(400))
      .andExpect(jsonPath("$.message").value("Email address already exists"));
  }
}
