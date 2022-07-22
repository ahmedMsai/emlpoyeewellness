package tn.esprit.wellness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.CollabRating;
import tn.esprit.wellness.repository.CollabRatingRepository;

@Service
public class CollabRatingService implements ICollabRatingService{

	@Autowired
	CollabRatingRepository collabRatingRepository;
	

	@Override
	public List<CollabRating> getAllCollabRatings() {
		return collabRatingRepository.findAll();
	}

	@Override
	public CollabRating addCollabRating(CollabRating collabRating) {
		return collabRatingRepository.save(collabRating);
	}

	@Override
	public void deleteCollabRatingById(int id) {
		collabRatingRepository.deleteById(id);
	}

	@Override
	public CollabRating updateCollabRating(CollabRating collabRating) {
		return collabRatingRepository.save(collabRating);
	}

	@Override
	public CollabRating getCollabRatingById(int id) {
		return collabRatingRepository.findById(id).get();
	}

	@Override
	public Double getCollaboratorRatingById(int CollabId) {
		return collabRatingRepository.calculateCollaboratorRating(CollabId);
	}

}
