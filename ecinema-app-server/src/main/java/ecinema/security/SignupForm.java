package ecinema.security;

import ecinema.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupForm {

    @NotBlank
    private String username;
    @Size(min = 4)
    private String password;
    @NotBlank
    private String email;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {

        return new User(username, passwordEncoder.encode(password), email, phoneNumber);
    }
}
