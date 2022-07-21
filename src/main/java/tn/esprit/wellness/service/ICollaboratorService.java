package tn.esprit.wellness.service;

import java.util.List;

import tn.esprit.wellness.entity.Collaborator;


public interface ICollaboratorService {
	
	Collaborator getCollaboratorById(int id);
	List<Collaborator> getAllCollaborations();
	int addCollaboration (Collaborator collaboration);
	void deleteCollaborationById (int id);
	Collaborator updateCollaboration (Collaborator collaboration);
}
