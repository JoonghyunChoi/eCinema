package ecinema.api;

import ecinema.domain.Comment;
import ecinema.domain.Post;
import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Post.class);
        config.exposeIdsFor(Comment.class);
        config.exposeIdsFor(Reservation.class);
        config.exposeIdsFor(User.class);
        config.useHalAsDefaultJsonMediaType(false);
    }
}
