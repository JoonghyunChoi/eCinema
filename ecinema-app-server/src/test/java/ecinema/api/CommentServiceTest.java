package ecinema.api;


import ecinema.data.CommentRepository;
import ecinema.domain.Comment;
import ecinema.domain.CommentForm;
import ecinema.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private Post post;

    private CommentForm parentCommentForm;

    private CommentForm childCommentForm;

    private Comment parentComment;

    private Comment childComment;

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
        parentComment.setId(Long.valueOf(1));

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


    @Test
    public void getCommentLists() {

        List<Comment> parentList = new ArrayList<>();
        List<Comment> childList = new ArrayList<>();
        List<List<Comment>> commentLists = new ArrayList<>();

        parentList.add(parentComment);
        childList.add(childComment);

        commentLists.add(parentList);
        commentLists.add(childList);

        assertThat(commentService.getCommentLists(comments)).isEqualTo(commentLists);
    }

    @Test
    public void saveComment() {

        when(commentRepository.save(parentComment)).thenReturn(parentComment);

        assertThat(commentService.saveComment(parentComment)).isEqualTo(parentComment);
    }

    @Test
    public void findCommentByPostId() {

        when(commentRepository.findByPostId(post.getId())).thenReturn(comments);

        assertThat(commentService.findCommentByPostId(post.getId())).isEqualTo(comments);
    }
}
