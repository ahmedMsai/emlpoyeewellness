package tn.esprit.wellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Advertisement;
import tn.esprit.wellness.entity.CollabRating;

@Repository
public interface CollabRatingRepository  extends JpaRepository<CollabRating, Integer>{
	@Query("SELECT avg(r.rate) FROM CollabRating r WHERE r.collaborator.id=:collabId")
	public Double calculateCollaboratorRating(@Param("collabId")int collabId);
}
