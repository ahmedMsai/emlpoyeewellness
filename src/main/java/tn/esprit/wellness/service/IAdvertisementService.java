package tn.esprit.wellness.service;

import java.util.List;

import tn.esprit.wellness.entity.Advertisement;

public interface IAdvertisementService {
	Advertisement getAdvertisementById(int id);
	List<Advertisement> getAllAdvertisements();
	List<Advertisement> getAdvertisementsByCollaborator(int collabId);
	Advertisement addAdvertisement (Advertisement advertisement,int collabId);
	void deleteAdvertisementById (int id);
	Advertisement updateAdvertisement (Advertisement advertisement);
}
