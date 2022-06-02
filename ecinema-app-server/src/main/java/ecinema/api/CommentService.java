package ecinema.api;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ecinema.domain.Comment;
import ecinema.data.CommentRepository;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<List<Comment>> getCommentLists(List<Comment> comments) {
        List<Comment> parentCommentList = new ArrayList<>();
        List<Comment> childCommentList = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getParentId() == 0) {
                parentCommentList.add(comment);
            }
            else {
                childCommentList.add(comment);
            }
        }

        List<List<Comment>> commentLists = new ArrayList<>();
        commentLists.add(parentCommentList);
        commentLists.add(childCommentList);
        return commentLists;
    }

    public List<Comment> findCommentByPostId(Long id) {
        return commentRepository.findByPostId(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}