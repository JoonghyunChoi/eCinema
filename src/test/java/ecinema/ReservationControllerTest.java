package ecinema;

import com.google.gson.Gson;
import ecinema.api.ReservationService;
import ecinema.domain.ReservationForm;
import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;


/* @WebMvcTest(controllers = {ReservationController.class}, useDefaultFilters = false) */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReservationService reservationService;


    @Test
    public void reserve() throws Exception {

        final ReservationForm reservationForm = new ReservationForm();
        reservationForm.setPrice("6,000");
        reservationForm.setUserId(Long.valueOf(1));
        reservationForm.setMovieId("1");
        reservationForm.setRsvDate("9/1");
        reservationForm.setRsvTime("24:00");
        reservationForm.setCcCVV("123");
        reservationForm.setCcNumber("234");
        reservationForm.setCcExpiration("345");

        final Reservation reservation = reservationForm.toReservation(new User());


        Mockito.when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);


        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
               .content(new Gson().toJson(reservationForm))
               .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
