package ecinema.api;


import ecinema.data.ReservationRepository;
import ecinema.data.UserRepository;
import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepo;
    @Autowired
    private UserRepository userRepo;

    @PostMapping
    @ResponseBody
    public EntityModel<Reservation> postReservation(@Validated @RequestBody ReservationForm reservationForm) throws IllegalArgumentException {

        if (reservationForm.getPrice().equals("6,000")) {

            User user = userRepo.getById(reservationForm.getUserId());
            Reservation reservation = reservationForm.toReservation(user);

            EntityModel<Reservation> reserve = EntityModel.of(reservationRepo.save(reservation));
            reserve.add(linkTo(methodOn(ReservationController.class).postReservation(reservationForm)).withSelfRel());

            return reserve;
        }
        throw new IllegalArgumentException("주문 가격이 실제 가격과 다릅니다.");
    }
}
