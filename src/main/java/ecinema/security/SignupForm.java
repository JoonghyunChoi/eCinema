package ecinema.security;

import ecinema.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignupForm {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {

        return new User(username, passwordEncoder.encode(password), email, phoneNumber);
    }
}
