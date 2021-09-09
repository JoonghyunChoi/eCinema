package ecinema.domain;

import lombok.Data;

@Data
public class CommentForm {

    private Long postId;
    private Long parentId;
    private String writer;
    private String content;

    public Comment toComment(Post post) {

        Comment comment = Comment.builder()
                .post(post)
                .parentId(parentId)
                .writer(writer)
                .content(content)
                .build();

        return comment;
    }
}
