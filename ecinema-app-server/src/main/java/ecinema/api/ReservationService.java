package ecinema.api;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ecinema.domain.Reservation;
import ecinema.data.ReservationRepository;

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