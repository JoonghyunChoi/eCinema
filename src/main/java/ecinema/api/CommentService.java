package ecinema.api;


import ecinema.data.CommentRepository;
import ecinema.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public List<List<Comment>> getCommentLists(List<Comment> comments) {

        List<Comment> parentList = new ArrayList<>();
        List<Comment> childList = new ArrayList<>();

        for (Comment comment : comments) {
            if (comment.getParentId() == 0) {
                parentList.add(comment);
            }
            else {
                childList.add(comment);
            }
        }

        List<List<Comment>> commentLists = new ArrayList<>();
        commentLists.add(parentList);
        commentLists.add(childList);

        return commentLists;
    }

    public List<Comment> findCommentByPostId(Long id) {
        return commentRepository.findByPostId(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
