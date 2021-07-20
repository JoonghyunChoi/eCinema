package ecinema.security;


import ecinema.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/login")
public class LoginController {

    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private UserRepositoryUserDetailsService userRepositoryUserDetailsService;

    @Autowired
    public LoginController(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, UserRepositoryUserDetailsService userRepositoryUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepositoryUserDetailsService = userRepositoryUserDetailsService;
    }

    @PostMapping(consumes = "application/json")
    public String login(@RequestBody User user) throws IllegalArgumentException {
        UserDetails member = userRepositoryUserDetailsService.loadUserByUsername(user.getUsername());

        if (member != null) {
            if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }
            return jwtTokenProvider.createToken(member.getUsername());
        }
        throw new IllegalArgumentException("Invalid login");
    }
}
