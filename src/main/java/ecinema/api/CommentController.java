package ecinema.api;


import ecinema.domain.Comment;
import ecinema.domain.CommentForm;
import ecinema.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/comments")
@RepositoryRestController
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    private final CommentService commentService;


    @GetMapping("/{id}")
    @ResponseBody
    public CollectionModel<List<Comment>> getComments(@PathVariable("id") Long id) {

        List<Comment> commentList = commentService.findCommentByPostId(id);

        CollectionModel<List<Comment>> comments = CollectionModel.of(commentService.getCommentLists(commentList));
        comments.add(linkTo(methodOn(CommentController.class).getComments(id)).withSelfRel());

        return comments;
    }

    @PostMapping("/parent_comment")
    @ResponseBody
    public EntityModel<Comment> postParentComment(@RequestBody CommentForm parentCommentForm) {

        Post post = postService.getPostById(parentCommentForm.getPostId());
        Comment parentComment = parentCommentForm.toComment(post);

        EntityModel<Comment> comment = EntityModel.of(commentService.saveComment(parentComment));
        comment.add(linkTo(methodOn(CommentController.class).postParentComment(parentCommentForm)).withSelfRel());

        return comment;
    }

    @PostMapping("/child_comment")
    @ResponseBody
    public EntityModel<Comment> postChildComment(@RequestBody CommentForm childCommentForm) {

        Post post = postService.getPostById(childCommentForm.getPostId());
        Comment childComment = childCommentForm.toComment(post);

        EntityModel<Comment> comment = EntityModel.of(commentService.saveComment(childComment));
        comment.add(linkTo(methodOn(CommentController.class).postChildComment(childCommentForm)).withSelfRel());

        return comment;
    }
}
