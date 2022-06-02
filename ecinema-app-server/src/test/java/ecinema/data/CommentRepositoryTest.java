package ecinema.data;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ecinema.domain.Comment;
import ecinema.domain.Post;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setup() {
        post = new Post();
        post.setTitle("a");
        post.setContent("b");
        post.setWriter("c");

        comment = new Comment();
        comment.setPost(post);
        comment.setParentId(post.getId());
        comment.setWriter("a");
        comment.setContent("b");
    }

    @Test
    public void findCommentByPostId() {
        postRepository.save(post);
        commentRepository.save(comment);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        assertThat(commentRepository.findByPostId(post.getId())).isEqualTo(comments);
    }

    @Test
    public void saveComment() {
        assertThat(commentRepository.save(comment)).isEqualTo(comment);
    }
}