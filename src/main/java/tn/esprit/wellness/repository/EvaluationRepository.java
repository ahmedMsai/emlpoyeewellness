package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Evaluation;

@Repository
public interface EvaluationRepository {
	@Query("SELECT p FROM Evaluation p WHERE p.id=:id LIMIT 1")
	public Evaluation findById(@Param("id")String id);
}
