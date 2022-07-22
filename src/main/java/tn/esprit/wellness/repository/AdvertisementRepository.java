package tn.esprit.wellness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer>{
	@Query("SELECT a FROM Advertisement a WHERE a.collaborator.id=:collabId")
	public List<Advertisement> findByCollaborator(@Param("collabId")int collabId);
}
