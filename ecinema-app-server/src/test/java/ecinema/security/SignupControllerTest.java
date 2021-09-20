package ecinema.security;


import com.google.gson.Gson;
import ecinema.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SignupController.class)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private SignupForm signupForm;

    @BeforeEach
    public void setup() {

        signupForm = new SignupForm();
        signupForm.setUsername("a");
        signupForm.setPassword("123456");
        signupForm.setEmail("abc");
        signupForm.setPhoneNumber("123");
    }


    @Test
    @WithMockUser
    public void signup() throws Exception {

        User user = signupForm.toUser(passwordEncoder);

        when(customUserDetailsService.saveUser(user)).thenReturn(user);

        mockMvc.perform(post("/signup")
                .content(new Gson().toJson(signupForm))
                .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void signupFormPasswordSizeException() throws Exception {

        signupForm.setPassword("123");

        mockMvc.perform(post("/signup")
                .content(new Gson().toJson(signupForm))
                .contentType(MediaType.APPLICATION_JSON)
                    .with(csrf()))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
