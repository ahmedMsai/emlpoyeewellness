package tn.esprit.wellness.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.wellness.entity.Event;
import tn.esprit.wellness.entity.Theme;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
	/*
	 * @Query("SELECT p FROM event p WHERE p.id=:id LIMIT 1") public Event
	 * findById(@Param("id")String id);
	 */

	@Query("SELECT DISTINCT e FROM Event e, User u WHERE e.user=u AND u.department=:depId ")
	public List<Event> findByDepId(@Param("depId") String depId);

	@Query("SELECT DISTINCT e FROM Event e, User u WHERE e.user=u AND u.bureau=:bId ")
	public List<Event> findByBureauId(@Param("bId") String bId);
	
	@Query("SELECT theme FROM Event e WHERE id=:eventId ")
	public String getThemeFromEvt(@Param("eventId") int eventId);
	
	@Query("SELECT id FROM User WHERE genre='HOMME' ")
	public int[] getMenUsers();
	
	@Query("SELECT id FROM User WHERE genre='FEMME' ")
	public int[] getWomenUsers();
	
	@Query("SELECT id FROM User")
	public int[] getAllUsers();
	
	@Query("SELECT id FROM User WHERE genre='HOMME' AND department=:depId")
	public int[] getMenUsersFromDepartment(@Param("depId") String depId);
	
	@Query("SELECT id FROM User WHERE genre='FEMME' AND department=:depId")
	public int[] getWomenUsersFromDepartment(@Param("depId") String depId);
	
	@Query("SELECT id FROM User WHERE department=:depId")
	public int[] getAllUsersFromDepartment(@Param("depId") String depId);

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
	@Query(value = "INSERT INTO invitation (id_user, id_event) VALUES (:userId, :eventId)", nativeQuery = true)
	public void inviteCoworkerHomme(@Param("userId") int userId, @Param("eventId") int eventId);

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
	@Query(value = "INSERT INTO notecommentaire (id_user, id_event, commentaire, note, date) VALUES (:userId, :eventId, :commentaire, :note, :date)", nativeQuery = true)
	public void noteCommentaire(@Param("userId") int userId, @Param("eventId") int eventId,
			@Param("commentaire") String commentaire, @Param("note") int note, @Param("date") Date date);
     
	
	@Modifying
	@Transactional
	@Query(value = "update STAT set moyenne = (SELECT AVG(note) FROM notecommentaire, event where (event.id= notecommentaire.id_event) and event.theme =:themeId group by theme) where STAT.theme=:themeId ", nativeQuery = true)
	public void generateStat(@Param("themeId") String themeId);

}
