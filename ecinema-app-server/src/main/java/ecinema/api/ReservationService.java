package ecinema.api;


import ecinema.data.ReservationRepository;
import ecinema.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public boolean checkPrice(String price) {

        if (price.equals("6,000")) {
            return true;
        }
        return false;
    }

    public Reservation saveReservation(Reservation reservation) {

        return reservationRepository.save(reservation);
    }
}
