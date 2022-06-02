package ecinema.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import lombok.RequiredArgsConstructor;
import ecinema.domain.Comment;
import ecinema.domain.CommentForm;
import ecinema.domain.Post;
import java.util.List;

@RequestMapping("/comments")
@RepositoryRestController
@RequiredArgsConstructor
public class CommentController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    @ResponseBody
    public CollectionModel<List<Comment>> getComments(@PathVariable("id") Long id) {
        List<Comment> comments = commentService.findCommentByPostId(id);
        List<List<Comment>> commentLists = commentService.getCommentLists(comments);

        CollectionModel<List<Comment>> collectionModel = CollectionModel.of(commentLists);
        collectionModel.add(linkTo(methodOn(CommentController.class).getComments(id)).withSelfRel());
        return collectionModel;
    }

    @PostMapping("/parent_comment")
    @ResponseBody
    public EntityModel<Comment> postParentComment(@RequestBody CommentForm parentCommentForm) {
        Post post = postService.getPostById(parentCommentForm.getPostId());
        Comment parentComment = parentCommentForm.toComment(post);

        EntityModel<Comment> entityModel = EntityModel.of(commentService.saveComment(parentComment));
        entityModel.add(linkTo(methodOn(CommentController.class).postParentComment(parentCommentForm)).withSelfRel());
        return entityModel;
    }

    @PostMapping("/child_comment")
    @ResponseBody
    public EntityModel<Comment> postChildComment(@RequestBody CommentForm childCommentForm) {
        Post post = postService.getPostById(childCommentForm.getPostId());
        Comment childComment = childCommentForm.toComment(post);

        EntityModel<Comment> entityModel = EntityModel.of(commentService.saveComment(childComment));
        entityModel.add(linkTo(methodOn(CommentController.class).postChildComment(childCommentForm)).withSelfRel());
        return entityModel;
    }
}