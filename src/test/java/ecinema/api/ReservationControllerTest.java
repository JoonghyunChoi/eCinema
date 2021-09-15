package ecinema.api;


import com.google.gson.Gson;
import ecinema.domain.Reservation;
import ecinema.domain.ReservationForm;
import ecinema.domain.User;
import ecinema.security.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.when;

//@WebMvcTest(controllers = {ReservationController.class}, useDefaultFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
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
    public void postReservation() throws Exception {

        when(customUserDetailsService.getUserById(reservationForm.getUserId())).thenReturn(user);
        when(reservationService.saveReservation(reservation)).thenReturn(reservation);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .content(new Gson().toJson(reservationForm))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
