package tn.esprit.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.Offer;
import tn.esprit.wellness.service.OfferService;

@RestController
@RequestMapping(value = "/api/auth/offer")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class OfferConroller {
	@Autowired
	private OfferService offerService;

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = "/")
	public Offer addOffer(@RequestBody Offer offer) {
		return offerService.addOffer(offer);
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(value = "/all")
	public List<Offer> getAllOffers(){
		return offerService.getAllOffers();
	}

	@GetMapping(value = "/active")
	public List<Offer> getActiveOffers(){
		return offerService.getActiveOffers();
	}
	
	
	@GetMapping(value = "/draft")
	public List<Offer> getDraftOffers(){
		return offerService.getDraftOffers();
	}
	
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(value = "/expired")
	public List<Offer> getExpiredOffers(){
		return offerService.getExpiredOffers();
	}

	
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	ResponseEntity<String> deleteOffer(@PathVariable int id ) {
		
		try {
			Offer offer = offerService.getOfferById(id);
			try {
				offerService.deleteOfferById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Offer '" + offer.getLabel() + "' was deleted successfully");
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Offer '" + offer.getLabel() + "' could not be deleted");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offer with id '" + id + "' not found");
		}
		 
	}
	@GetMapping(value ="/{id}")
	 public Offer retriveOffert(@PathVariable int id) {
		
	    return offerService.getOfferById(id);
	  }

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping(value = "/")
	public Offer updateOffer(@RequestBody Offer offer)  {
	  return offerService.updateOffer(offer);
	}
}
