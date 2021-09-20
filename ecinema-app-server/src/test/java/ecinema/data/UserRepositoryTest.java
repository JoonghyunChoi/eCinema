package ecinema.data;


import ecinema.domain.User;
import ecinema.security.SignupForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    public void setup() {

        SignupForm signupForm = new SignupForm();
        signupForm.setUsername("a");
        signupForm.setPassword("1234");
        signupForm.setEmail("abc");
        signupForm.setPhoneNumber("123");

        user = signupForm.toUser(passwordEncoder);
    }


    @Test
    public void findByUsername() {

        userRepository.save(user);

        assertThat(userRepository.findByUsername(user.getUsername())).isEqualTo(user);
    }

    @Test
    public void saveUser() {

        assertThat(userRepository.save(user)).isEqualTo(user);
    }
}
