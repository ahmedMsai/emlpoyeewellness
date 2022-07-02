package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.wellness.entity.Collaboration;

public interface CollaborationRepository {
	@Query("SELECT p FROM Collaboration p WHERE p.id=:id LIMIT 1")
	public Collaboration findById(@Param("id")String id);
}
