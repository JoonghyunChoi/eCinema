package ecinema.api;

import ecinema.data.ReservationRepository;
import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

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
    public void saveReserve() {

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        assertThat(reservationService.saveReservation(reservation)).isEqualTo(reservation);
    }
}
