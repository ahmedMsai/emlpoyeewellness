package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Offer;

@Repository
public interface OfferRepository {
	@Query("SELECT p FROM Offer p WHERE p.id=:id LIMIT 1")
	public Offer findById(@Param("id")String id);
}
