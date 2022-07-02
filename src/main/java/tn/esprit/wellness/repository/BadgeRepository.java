package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Badge;

@Repository
public interface BadgeRepository {
	@Query("SELECT p FROM Badge p WHERE p.id=:id LIMIT 1")
	public Badge findById(@Param("id")String id);
}
