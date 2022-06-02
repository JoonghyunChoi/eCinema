package ecinema.domain;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ReservationForm {
    private Long userId;
    private String movieId;
    private String rsvDate;
    private String rsvTime;
    private String price;
    @NotBlank
    private String ccNumber;
    @NotBlank
    private String ccExpiration;
    @NotBlank
    private String ccCVV;

    public Reservation toReservation(User user) {
        Reservation reservation = Reservation.builder()
                .user(user)
                .movieId(movieId)
                .rsvDate(rsvDate)
                .rsvTime(rsvTime)
                .price(price)
                .ccNumber(ccNumber)
                .ccExpiration(ccExpiration)
                .ccCVV(ccCVV)
                .build();

        return reservation;
    }
}