package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Event;

@Repository
public interface EventRepository {
	@Query("SELECT p FROM Event p WHERE p.id=:id LIMIT 1")
	public Event findById(@Param("id")String id);
}
