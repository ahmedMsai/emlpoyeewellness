package tn.esprit.wellness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.Advertisement;
import tn.esprit.wellness.entity.Collaborator;
import tn.esprit.wellness.repository.AdvertisementRepository;
import tn.esprit.wellness.repository.CollaboratorRepository;

@Service
public class AdvertisementService implements IAdvertisementService {
	
	@Autowired
	AdvertisementRepository advertisementRepository;
	@Autowired
	CollaboratorRepository collaboratorRepository;

	@Override
	public Advertisement addAdvertisement(Advertisement advertisement,int collabId) {
		Collaborator collaborator = collaboratorRepository.findById(collabId).get();
		advertisement.setCollaborator(collaborator);
		return advertisementRepository.save(advertisement);
	}

	@Override
	public void deleteAdvertisementById(int id) {
       advertisementRepository.deleteById(id);	
	}

	@Override
	public Advertisement updateAdvertisement(Advertisement advertisement) {
		if (advertisementRepository.findById(advertisement.getId())==null) {
			return null;	
		}	
		return advertisementRepository.save(advertisement);
	}

	@Override
	public List<Advertisement> getAllAdvertisements() {
		return advertisementRepository.findAll();
	}

	@Override
	public Advertisement getAdvertisementById(int id) {
		return advertisementRepository.findById(id).get();	
	}

	@Override
	public List<Advertisement> getAdvertisementsByCollaborator(int collabId) {
		//return advertisementRepository.findByCollaborator(collabId);
		return null;
	}

}
