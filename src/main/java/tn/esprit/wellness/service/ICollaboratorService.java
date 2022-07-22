package tn.esprit.wellness.service;

import java.util.List;

import tn.esprit.wellness.entity.Collaborator;


public interface ICollaboratorService {
	
	Collaborator getCollaboratorById(int id);
	List<Collaborator> getAllCollaborators();
	Collaborator addCollaborator (Collaborator collaborator);
	void deleteCollaboratorById (int id);
	Collaborator updateCollaborator (Collaborator collaborator);
}
