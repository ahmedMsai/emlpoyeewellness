package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Comment;

@Repository
public interface CommentRepository {
	@Query("SELECT p FROM Comment p WHERE p.id=:id LIMIT 1")
	public Comment findById(@Param("id")String id);
}
