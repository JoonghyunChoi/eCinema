package ecinema.api;

import ecinema.domain.Reservation;
import ecinema.domain.User;
import lombok.Data;

@Data
public class ReservationForm {

    private Long userId;
    private String movieId;
    private String rsvDate;
    private String rsvTime;
    private String price;
    private String ccNumber;
    private String ccExpiration;
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
