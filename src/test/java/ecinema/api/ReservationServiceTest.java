package ecinema.api;

import ecinema.data.ReservationRepository;
import ecinema.domain.Reservation;
import ecinema.domain.ReservationForm;
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

    private ReservationForm reservationForm;

    private Reservation reservation;

    @BeforeEach
    public void setup() {

        user = new User();
        user.setUsername("a");
        user.setPassword("123");
        user.setEmail("b");
        user.setPhoneNumber("234");

        reservationForm = new ReservationForm();
        reservationForm.setUserId(user.getId());
        reservationForm.setPrice("6,000");
        reservationForm.setMovieId("1");
        reservationForm.setRsvDate("9/1");
        reservationForm.setRsvTime("24:00");
        reservationForm.setCcCVV("123");
        reservationForm.setCcNumber("234");
        reservationForm.setCcExpiration("345");

        reservation = reservationForm.toReservation(user);
    }


    @Test
    public void checkPriceTrue() {

        assertThat(reservationService.checkPrice(reservationForm.getPrice())).isEqualTo(true);
    }

    @Test
    public void checkPriceFalse() {

        reservationForm.setPrice("600");

        assertThat(reservationService.checkPrice(reservationForm.getPrice())).isEqualTo(false);
    }

    @Test
    public void saveReserve() {

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        assertThat(reservationService.saveReservation(reservation)).isEqualTo(reservation);
    }
}
