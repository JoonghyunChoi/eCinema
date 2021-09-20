package ecinema.security;


import ecinema.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

import static ecinema.security.JwtTokenProvider.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    private Key key;

    private String token;

    private User user;


    @BeforeEach
    public void setup() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        key = Keys.hmacShaKeyFor(keyBytes);

        user = new User();
        user.setUsername("a");
        user.setPassword("123");
        user.setEmail("b");
        user.setPhoneNumber("234");
        user.setId(Long.valueOf(1));

        when(authentication.getName()).thenReturn(user.getUsername());
        when(authentication.getPrincipal()).thenReturn(user);

        token = jwtTokenProvider.createToken(authentication);
    }

    @Test
    public void createToken() {

        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        System.out.println(claims);

        assertThat(claims.get("username")).isEqualTo(user.getUsername());
        assertThat(claims.get("userId")).isEqualTo(user.getId().toString());
    }

    @Test
    public void getAuthentication() {

        UserDetails userDetails = (UserDetails) user;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(user);

        assertThat(jwtTokenProvider.getAuthentication(token)).isEqualTo(usernamePasswordAuthenticationToken);
    }

    @Test
    public void resolveToken() {

        String bearerToken = BEARER_PREFIX + token;

        when(httpServletRequest.getHeader(AUTHORIZATION_HEADER)).thenReturn(bearerToken);

        assertThat(jwtTokenProvider.resolveToken(httpServletRequest)).isEqualTo(token);
    }

    @Test
    public void getUsername() {

        assertThat(jwtTokenProvider.getUsername(token)).isEqualTo(user.getUsername());
    }

    @Test
    public void validateToken() {

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    public void validateTokenSignatureException() {

        token += "abc123";

        assertThat(jwtTokenProvider.validateToken(token)).isFalse();
    }
}
