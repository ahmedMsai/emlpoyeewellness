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

import tn.esprit.wellness.entity.Advertisement;
import tn.esprit.wellness.service.AdvertisementService;

@RestController
@RequestMapping(value = "/api/auth/advertisement")
public class AdvertisementController {
	@Autowired
	private AdvertisementService advertisementService;

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@PostMapping(value = "/{collabId}")
	public Advertisement addAdvertisement(@RequestBody Advertisement advertisement,@PathVariable int collabId ) {
		return advertisementService.addAdvertisement(advertisement,collabId);
	}

	@GetMapping(value = "/all")
	public List<Advertisement> getAllAdvertisements(){
		return advertisementService.getAllAdvertisements();
	}
	
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	ResponseEntity<String> deleteAdvertisement(@PathVariable int id ) {
		
		try {
			Advertisement advertisement = advertisementService.getAdvertisementById(id);
			try {
				advertisementService.deleteAdvertisementById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Advertisement '" + advertisement.getLabel() + "' was deleted successfully");
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Advertisement '" + advertisement.getLabel() + "' could not be deleted");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement with id '" + id + "' not found");
		}
		 
	}
	
	@GetMapping(value ="/{id}")
	 public Advertisement retriveAdvertisementt(@PathVariable int id) {
		
	    return advertisementService.getAdvertisementById(id);
	  }

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@PutMapping(value = "/")
	public Advertisement updateAdvertisement(@RequestBody Advertisement advertisement)  {
	  return advertisementService.updateAdvertisement(advertisement);
	}
}
