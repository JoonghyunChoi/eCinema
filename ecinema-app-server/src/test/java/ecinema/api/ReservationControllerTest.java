package ecinema.api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import ecinema.domain.Reservation;
import ecinema.domain.ReservationForm;
import ecinema.domain.User;
import ecinema.security.CustomUserDetailsService;

@WebMvcTest(controllers=ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
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
        user.setId(Long.valueOf(1));

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
    @WithMockUser
    public void postReservation() throws Exception {
        when(reservationService.checkPrice(reservationForm.getPrice())).thenReturn(true);
        when(customUserDetailsService.getUserById(reservationForm.getUserId())).thenReturn(user);
        when(reservationService.saveReservation(reservation)).thenReturn(reservation);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .content(new Gson().toJson(reservationForm))
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()));
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    public  void postReservationException() throws Exception {
        when(reservationService.checkPrice(reservationForm.getPrice())).thenReturn(false);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .content(new Gson().toJson(reservationForm))
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()));
        resultActions
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }
}