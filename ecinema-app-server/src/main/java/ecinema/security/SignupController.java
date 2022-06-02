package ecinema.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import ecinema.domain.User;

@RequestMapping("/signup")
@RequiredArgsConstructor
@RestController
public class SignupController {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User signup(@RequestBody @Validated SignupForm signupForm) {
        return customUserDetailsService.saveUser(signupForm.toUser(passwordEncoder));
    }
}