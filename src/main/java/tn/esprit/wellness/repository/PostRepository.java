package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Post;

@Repository
public interface PostRepository {

	@Query("SELECT p FROM Post p WHERE p.id=:id LIMIT 1")
	public Post findById(@Param("id")String id);
}
