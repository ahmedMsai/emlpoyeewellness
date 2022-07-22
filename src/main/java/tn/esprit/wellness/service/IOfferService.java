package tn.esprit.wellness.service;

import java.util.List;

import tn.esprit.wellness.entity.Offer;

public interface IOfferService {
	Offer getOfferById(int id);
	List<Offer> getAllOffers();
	List<Offer> getOffersByAdvertisement(int adId);
	List<Offer> getActiveOffers();
	List<Offer> getActiveOffersByAdvertisement(int adId);
	List<Offer> getDraftOffers();
	List<Offer> getExpiredOffers();
	Offer addOffer (Offer offer);
	void deleteOfferById (int id);
	Offer updateOffer (Offer offer);
}
