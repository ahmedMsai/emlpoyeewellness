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
	public List<Collaborator> getAllCollaborators() {
		return collaboratorRepository.findAll();
	}

	@Override
	public Collaborator addCollaborator(Collaborator collaborator) {
		return collaboratorRepository.save(collaborator);
	}

	@Override
	public void deleteCollaboratorById(int id) {
		collaboratorRepository.deleteById(id);
	}

	@Override
	public Collaborator updateCollaborator(Collaborator collaborator) {
		return collaboratorRepository.save(collaborator);
	}

	@Override
	public Collaborator getCollaboratorById(int id) {
		return collaboratorRepository.findById(id).get();
	}

}
