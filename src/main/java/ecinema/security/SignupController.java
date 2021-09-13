package ecinema.security;

import ecinema.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;


    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User Signup(@Validated @RequestBody SignupForm signupForm) {

        return customUserDetailsService.saveUser(signupForm.toUser(passwordEncoder));
    }
}
