package ecinema.security;

import ecinema.data.UserRepository;
import ecinema.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/signup")
public class SignupController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User Signup(@RequestBody SignupForm signupForm) {

        return userRepo.save(signupForm.toUser(passwordEncoder));
    }
}
