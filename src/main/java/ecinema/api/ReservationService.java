package ecinema.api;


import ecinema.data.ReservationRepository;
import ecinema.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }


    public Reservation saveReservation(Reservation reservation) {

        return reservationRepository.save(reservation);
    }
}
