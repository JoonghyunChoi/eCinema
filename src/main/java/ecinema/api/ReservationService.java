package ecinema.api;


import ecinema.data.ReservationRepository;
import ecinema.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public Reservation saveReservation(Reservation reservation) {

        return reservationRepository.save(reservation);
    }
}
