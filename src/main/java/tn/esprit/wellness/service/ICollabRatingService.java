package tn.esprit.wellness.service;

import java.util.List;

import tn.esprit.wellness.entity.CollabRating;

public interface ICollabRatingService {
	CollabRating getCollabRatingById(int id);
	List<CollabRating> getAllCollabRatings();
	CollabRating addCollabRating (CollabRating collabRating);
	void deleteCollabRatingById (int id);
	CollabRating updateCollabRating (CollabRating collabRating);
	Double getCollaboratorRatingById(int CollabId);
}
