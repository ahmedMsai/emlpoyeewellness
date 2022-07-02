package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Reservation;

@Repository
public interface ReservationRepository {
	@Query("SELECT p FROM Reservation p WHERE p.id=:id LIMIT 1")
	public Reservation findById(@Param("id")String id);
}
