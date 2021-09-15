package ecinema.api;


import ecinema.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
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


    @Test
    public void getPosts() throws Exception {

        when(postService.findAllPost(any(Pageable.class))).thenReturn(posts);
        when(postService.getPageIndexes(anyInt())).thenReturn(pageIndexes);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts?page=0"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
