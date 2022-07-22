package tn.esprit.wellness.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer>{
	@Query("SELECT o FROM Offer o WHERE o.startingFrom<:now AND o.expireBy>:now")
	public List<Offer> findActive(@Param("now")Date now);
	
	@Query("SELECT o FROM Offer o WHERE o.startingFrom>:now")
	public List<Offer> findDraft(@Param("now")Date now);
	
	@Query("SELECT o FROM Offer o WHERE o.expireBy<:now")
	public List<Offer> findExpired(@Param("now")Date now);
	
	@Query("SELECT o FROM Offer o WHERE o.startingFrom<:now AND o.expireBy>:now AND o.advertisement.id=:adId")
	public List<Offer> getActiveOffersByAdvertisement(@Param("now")Date now,@Param("adId")int adId);
	
	@Query("SELECT o FROM Offer o WHERE o.advertisement.id=:adId")
	public List<Offer> getOffersByAdvertisement(@Param("adId")int adId);
}
