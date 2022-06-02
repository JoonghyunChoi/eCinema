package ecinema.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ecinema.domain.Post;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers=PostController.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;
    private Post post;
    private List<Post> postList;
    private Pageable pageable;
    private Page<Post> posts;
    private List<Integer> pageIndexes;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setTitle("a");
        post.setContent("b");
        post.setWriter("c");
        post.setId(Long.valueOf(1));

        postList = new ArrayList<>();
        postList.add(post);
        pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        posts = new PageImpl<>(postList, pageable, postList.size());

        pageIndexes = new ArrayList<>();
        pageIndexes.add(0);
    }

    @WithMockUser
    @Test
    public void getPosts() throws Exception {
        when(postService.findAllPost(any(Pageable.class))).thenReturn(posts);
        when(postService.getPageIndexes(anyInt())).thenReturn(pageIndexes);

        mockMvc.perform(get("/posts?page=0")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}