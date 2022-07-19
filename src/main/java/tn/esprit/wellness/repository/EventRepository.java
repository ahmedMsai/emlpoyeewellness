package tn.esprit.wellness.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.wellness.entity.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	/*
	 * @Query("SELECT p FROM event p WHERE p.id=:id LIMIT 1") public Event
	 * findById(@Param("id")String id);
	 */

	@Query("SELECT e FROM Event e, User u WHERE u.department=:depId ")
	public List<Event> findByDepId(@Param("depId") String depId);

	@Query("SELECT e FROM Event e, User u WHERE u.bureau=:bId ")
	public List<Event> findByBureauId(@Param("bId") String bId);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO PARTICIPATION (id_user, id_event) VALUES (:userId, :eventId)", nativeQuery = true)
	public void addParticipation(@Param("userId") int userId, @Param("eventId") int eventId);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO invitation (id_user, id_event) VALUES (:userId, :eventId)", nativeQuery = true)
	public void inviteCoworker(@Param("userId") int userId, @Param("eventId") int eventId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE invitation SET status= 'accepted' WHERE id_user=:userId AND id_event=:eventId", nativeQuery = true)
	public void acceptInvitation(@Param("userId") int userId, @Param("eventId") int eventId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE invitation SET status= 'refused' WHERE id_user=:userId AND id_event=:eventId", nativeQuery = true)
	public void refuseInvitation(@Param("userId") int userId, @Param("eventId") int eventId);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO note_commentaire (id_user, id_event, commentaire, note, date) VALUES (:userId, :eventId, :commentaire, :note, :date)", nativeQuery = true)
	public void noteCommentaire(@Param("userId") int userId, @Param("eventId") int eventId, @Param("commentaire") String commentaire, @Param("note") int note, @Param("date") Date date);

}
