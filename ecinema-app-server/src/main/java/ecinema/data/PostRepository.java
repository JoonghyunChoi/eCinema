package ecinema.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ecinema.domain.Post;

@RepositoryRestResource
public interface PostRepository extends JpaRepository<Post, Long> {}