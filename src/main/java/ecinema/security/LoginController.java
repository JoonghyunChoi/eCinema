package ecinema.security;


import ecinema.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepositoryUserDetailsService userRepositoryUserDetailsService;

    @PostMapping(consumes = "application/json")
    public String login(@RequestBody User user) throws IllegalArgumentException {
        User member = userRepositoryUserDetailsService.loadUserByUsername(user.getUsername());

        if (member != null) {
            if (!passwordEncoder.matches(user.getPassword(), member.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }
            return jwtTokenProvider.createToken(member.getId(), member.getUsername());
        }
        throw new IllegalArgumentException("Invalid login");
    }
}
