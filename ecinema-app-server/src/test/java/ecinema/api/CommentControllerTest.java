package ecinema.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.google.gson.Gson;
import ecinema.domain.Comment;
import ecinema.domain.CommentForm;
import ecinema.domain.Post;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers=CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    @MockBean
    private PostService postService;
    private Post post;
    private Comment parentComment;
    private Comment childComment;
    private CommentForm parentCommentForm;
    private CommentForm childCommentForm;
    private List<Comment> comments;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setTitle("a");
        post.setContent("b");
        post.setWriter("c");
        post.setId(Long.valueOf(1));

        parentCommentForm = new CommentForm();
        parentCommentForm.setPostId(post.getId());
        parentCommentForm.setParentId(Long.valueOf(0));
        parentCommentForm.setWriter("a");
        parentCommentForm.setContent("b");
        parentComment = parentCommentForm.toComment(post);

        childCommentForm = new CommentForm();
        childCommentForm.setPostId(post.getId());
        childCommentForm.setParentId(parentComment.getId());
        childCommentForm.setWriter("a");
        childCommentForm.setContent("b");
        childComment = childCommentForm.toComment(post);

        comments = new ArrayList<>();
        comments.add(parentComment);
        comments.add(childComment);
    }

    @WithMockUser
    @Test
    public void getComments() throws Exception {
        List<Comment> parentList = new ArrayList<>();
        List<Comment> childList = new ArrayList<>();
        List<List<Comment>> commentLists = new ArrayList<>();

        parentList.add(parentComment);
        childList.add(childComment);

        commentLists.add(parentList);
        commentLists.add(childList);

        Mockito.when(commentService.findCommentByPostId(post.getId())).thenReturn(comments);
        Mockito.when(commentService.getCommentLists(comments)).thenReturn(commentLists);

        mockMvc.perform(get("/comments/" + post.getId())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    public void postParentComment() throws Exception {
        Mockito.when(postService.getPostById(parentCommentForm.getPostId())).thenReturn(post);
        Mockito.when(commentService.saveComment(parentComment)).thenReturn(parentComment);

        mockMvc.perform(post("/comments/parent_comment")
                .content(new Gson().toJson(parentCommentForm))
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @Test
    public void postChildComment() throws Exception {
        Mockito.when(postService.getPostById(childCommentForm.getPostId())).thenReturn(post);
        Mockito.when(commentService.saveComment(childComment)).thenReturn(childComment);

        mockMvc.perform(post("/comments/child_comment")
                .content(new Gson().toJson(childCommentForm))
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}