package ecinema.security;


import ecinema.data.UserRepository;
import ecinema.domain.User;
import ecinema.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {

        user = new User();
        user.setUsername("a");
        user.setPassword("123");
        user.setEmail("b");
        user.setPhoneNumber("234");
        user.setId(Long.valueOf(1));
    }


    @Test
    public void loadUserByUsernameSuccess() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        assertThat(customUserDetailsService.loadUserByUsername(user.getUsername())).isEqualTo(user);
    }

    @Test
    public void loadUserByUsernameFail() {

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername(user.getUsername());
        });

        assertThat(exception.getMessage()).isEqualTo(String.format("사용자 '%s'을(를) 찾지 못했습니다.", user.getUsername()));
    }

    @Test
    public void getUserById() {

        when(userRepository.getById(user.getId())).thenReturn(user);

        assertThat(customUserDetailsService.getUserById(user.getId())).isEqualTo(user);
    }

    @Test
    public void saveUser() {

        when(userRepository.save(user)).thenReturn(user);

        assertThat(customUserDetailsService.saveUser(user)).isEqualTo(user);
    }
}
