package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.User;

@Repository
public interface UserRepository {
	@Query("SELECT p FROM User p WHERE p.id=:id LIMIT 1")
	public User findById(@Param("id")String id);
}
