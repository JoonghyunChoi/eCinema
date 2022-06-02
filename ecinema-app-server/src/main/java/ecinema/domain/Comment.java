package ecinema.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;
    private Long parentId;
    private String writer;
    private String content;
    private String createdAt;

    @Builder
    public Comment(Post post, Long parentId, String writer, String content) {
        this.post = post;
        this.parentId = parentId;
        this.writer = writer;
        this.content = content;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}