package ecinema.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ecinema.domain.Post;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    private Post post;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setTitle("a");
        post.setContent("b");
        post.setWriter("c");
        postRepository.save(post);
    }

    @Test
    public void findAllPost() {
        List<Post> posts = new ArrayList<>();
        posts.add(post);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));

        assertThat(postRepository.findAll(pageable)).isEqualTo(new PageImpl<>(posts, pageable, posts.size()));
    }
}