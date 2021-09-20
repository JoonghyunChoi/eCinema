package ecinema.data;


import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    private User user;

    private Reservation reservation;

    @BeforeEach
    public void setup() {

        user = new User();
        user.setUsername("a");
        user.setPassword("123");
        user.setEmail("b");
        user.setPhoneNumber("234");

        reservation = new Reservation();
        reservation.setUser(user);
        reservation.setMovieId("1");
        reservation.setPrice("6,000");
        reservation.setRsvDate("12/31");
        reservation.setRsvTime("12:00");
        reservation.setCcNumber("123");
        reservation.setCcExpiration("12/24");
        reservation.setCcCVV("255");
    }


    @Test
    public void save() {

        assertThat(reservationRepository.save(reservation)).isEqualTo(reservation);
    }
}
