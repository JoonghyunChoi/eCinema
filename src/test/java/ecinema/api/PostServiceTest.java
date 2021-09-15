package ecinema.api;


import ecinema.data.PostRepository;
import ecinema.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    public void setup() {

        post = new Post();
        post.setTitle("a");
        post.setContent("b");
        post.setWriter("c");
        post.setId(Long.valueOf(1));
    }


    @Test
    public void getPageIndexes() {

        List<Integer> pagesIndexes = new ArrayList<>();
        pagesIndexes.add(0);

        assertThat(postService.getPageIndexes(1)).isEqualTo(pagesIndexes);
    }

    @Test
    public void getPostById() {

        when(postRepository.getById(post.getId())).thenReturn(post);

        assertThat(postService.getPostById(post.getId())).isEqualTo(post);
    }

    @Test
    public void findAllPost() {

        List<Post> posts = new ArrayList<>();
        posts.add(post);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        Page<Post> page = new PageImpl<>(posts, pageable, posts.size());

        when(postRepository.findAll(pageable)).thenReturn(page);

        assertThat(postService.findAllPost(pageable)).isEqualTo(page);
    }
}
