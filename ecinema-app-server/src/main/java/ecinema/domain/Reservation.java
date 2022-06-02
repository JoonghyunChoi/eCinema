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
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    private String rsvDate;
    private String rsvTime;
    private String price;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private String createdAt;

    @Builder
    public Reservation(
            User user, String movieId, String rsvDate,
            String rsvTime, String price, String ccNumber,
            String ccExpiration, String ccCVV) {
        this.user = user;
        this.movieId = movieId;
        this.rsvDate = rsvDate;
        this.rsvTime = rsvTime;
        this.price = price;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}