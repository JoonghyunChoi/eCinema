package ecinema.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String movieId;
    private Date rsvDate;
    private String rsvTime;
    private String price;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;
    private Date createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
