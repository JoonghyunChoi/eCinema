package ecinema.security;

import ecinema.data.UserRepository;
import ecinema.domain.User;
import ecinema.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    

    @Override
    public User loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new EntityNotFoundException(String.format("사용자 '%s'을(를) 찾지 못했습니다.", username));
    }

    public User getUserById(Long id) {

        return userRepository.getById(id);
    }

    public User saveUser(User user) {

        return userRepository.save(user);
    }
}
