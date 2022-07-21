package tn.esprit.wellness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.Collaborator;
import tn.esprit.wellness.repository.CollaboratorRepository;

@Service
public class CollaboratorService implements ICollaboratorService{

	@Autowired
	CollaboratorRepository collaboratorRepository;
	

	@Override
	public List<Collaborator> getAllCollaborations() {
		// TODO Auto-generated method stub
		//return collaboratorRepository.;
	}

	@Override
	public int addCollaboration(Collaborator collaboration) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteCollaborationById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collaborator updateCollaboration(Collaborator collaboration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collaborator getCollaboratorById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
