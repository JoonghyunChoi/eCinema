package ecinema.api;


import ecinema.data.CommentRepository;
import ecinema.data.PostRepository;
import ecinema.domain.Comment;
import ecinema.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/comments")
@RepositoryRestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private PostRepository postRepo;

    @GetMapping("/{id}")
    @ResponseBody
    public CollectionModel<List<Comment>> getComments(@PathVariable("id") Long id) {

        List<Comment> commentList = commentRepo.findByPostId(id);
        List<Comment> parentList = new ArrayList<>();
        List<Comment> childList = new ArrayList<>();
        for (Comment comment : commentList) {
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

        CollectionModel<List<Comment>> comments = CollectionModel.of(commentLists);
        comments.add(linkTo(methodOn(CommentController.class).getComments(id)).withSelfRel());

        return comments;
    }

    @PostMapping("/parent_comments")
    @ResponseBody
    public EntityModel<Comment> postParentComments(@RequestBody CommentForm parentCommentForm) {

        Post post = postRepo.getById(parentCommentForm.getPostId());
        Comment parentComment = parentCommentForm.toComment(post);

        EntityModel<Comment> comment = EntityModel.of(commentRepo.save(parentComment));
        comment.add(linkTo(methodOn(CommentController.class).postParentComments(parentCommentForm)).withSelfRel());

        return comment;
    }

    @PostMapping("/child_comments")
    @ResponseBody
    public EntityModel<Comment> postChildComments(@RequestBody CommentForm childCommentForm) {
        Post post = postRepo.getById(childCommentForm.getPostId());
        Comment childComment = childCommentForm.toComment(post);

        EntityModel<Comment> comment = EntityModel.of(commentRepo.save(childComment));
        comment.add(linkTo(methodOn(CommentController.class).postChildComments(childCommentForm)).withSelfRel());

        return comment;
    }
}
