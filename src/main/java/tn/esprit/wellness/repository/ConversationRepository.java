package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Conversation;

@Repository
public interface ConversationRepository {
	@Query("SELECT p FROM Conversation p WHERE p.id=:id LIMIT 1")
	public Conversation findById(@Param("id")String id);
}
