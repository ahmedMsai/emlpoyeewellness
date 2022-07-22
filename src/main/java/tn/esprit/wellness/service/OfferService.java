package tn.esprit.wellness.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.Offer;
import tn.esprit.wellness.repository.OfferRepository;

@Service
public class OfferService implements IOfferService{
	@Autowired
	OfferRepository offerRepository;
	

	@Override
	public List<Offer> getAllOffers() {
		return offerRepository.findAll();
	}

	@Override
	public Offer addOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public void deleteOfferById(int id) {
		offerRepository.deleteById(id);
	}

	@Override
	public Offer updateOffer(Offer offer) {
		return offerRepository.save(offer);
	}

	@Override
	public Offer getOfferById(int id) {
		return offerRepository.findById(id).get();
	}

	@Override
	public List<Offer> getActiveOffers() {
		return offerRepository.findActive(new Date());
	}

	@Override
	public List<Offer> getDraftOffers() {
		return offerRepository.findDraft(new Date());
	}

	@Override
	public List<Offer> getExpiredOffers() {
		return offerRepository.findExpired(new Date());
	}

	@Override
	public List<Offer> getOffersByAdvertisement(int adId) {
		return null;// offerRepository.getOffersByAdvertisement(adId);
	}

	@Override
	public List<Offer> getActiveOffersByAdvertisement(int adId) {
		return null;//offerRepository.getActiveOffersByAdvertisement(new Date(),adId);
	}
}
