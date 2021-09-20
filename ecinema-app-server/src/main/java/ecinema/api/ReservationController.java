package ecinema.api;


import ecinema.domain.Reservation;
import ecinema.domain.ReservationForm;
import ecinema.domain.User;
import ecinema.exception.InvalidPriceException;
import ecinema.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("/reservations")
@RepositoryRestController
@RequiredArgsConstructor
public class ReservationController {

    private final CustomUserDetailsService customUserDetailsService;

    private final ReservationService reservationService;


    @PostMapping
    @ResponseBody
    public EntityModel<Reservation> postReservation(@RequestBody @Validated ReservationForm reservationForm) throws Exception {

        if (reservationService.checkPrice(reservationForm.getPrice())) {

            User user = customUserDetailsService.getUserById(reservationForm.getUserId());
            Reservation reservation = reservationForm.toReservation(user);

            EntityModel<Reservation> entityModel = EntityModel.of(reservationService.saveReservation(reservation));

            entityModel.add(linkTo(methodOn(ReservationController.class).postReservation(reservationForm)).withSelfRel());

            return entityModel;
        }
        throw new InvalidPriceException();
    }
}
