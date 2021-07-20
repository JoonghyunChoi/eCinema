package ecinema.data;

import ecinema.domain.Reservation;
import ecinema.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
