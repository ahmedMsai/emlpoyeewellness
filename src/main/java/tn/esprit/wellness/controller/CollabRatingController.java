package tn.esprit.wellness.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.CollabRating;
import tn.esprit.wellness.entity.Collaborator;
import tn.esprit.wellness.entity.User;
import tn.esprit.wellness.security.services.UserDetailsImpl;
import tn.esprit.wellness.service.CollabRatingService;
import tn.esprit.wellness.service.ICollaboratorService;
import tn.esprit.wellness.service.IUserService;

@RestController
@RequestMapping(value = "/api/auth/collabRating")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class CollabRatingController {

	@Autowired
	private CollabRatingService collabRatingService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ICollaboratorService collaboratorService;

	@PostMapping(value = "/{collabId}")
	public CollabRating addCollabRating(@RequestBody int rate,@PathVariable int collabId ) {
		UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user= userService.getUser(userDetails.getId());
		Collaborator collaborator= collaboratorService.getCollaboratorById(collabId);
		CollabRating collabRating = new CollabRating();
		collabRating.setCollaborator(collaborator);
		collabRating.setUser(user);
		collabRating.setRate(rate);
		return collabRatingService.addCollabRating(collabRating);
	}

	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping(value = "/all")
	public List<CollabRating> getAllCollabRatings(){
		return collabRatingService.getAllCollabRatings();
	}

	@DeleteMapping(value = "/{id}")
	ResponseEntity<String> deleteCollabRating(@PathVariable int id ) {
		
		try {
			CollabRating collabRating = collabRatingService.getCollabRatingById(id);
			if(!hasAccess(collabRating)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this rating!");
			}
			try {
				collabRatingService.deleteCollabRatingById(id);
				return ResponseEntity.status(HttpStatus.OK).body("CollabRating '" + collabRating.getId() + "' was deleted successfully");
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CollabRating '" + collabRating.getId() + "' could not be deleted");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CollabRating with id '" + id + "' not found");
		}
		 
	}

	@GetMapping(value ="/{id}")
	 public CollabRating retriveCollabRating(@PathVariable int id) {
		CollabRating collabRating = collabRatingService.getCollabRatingById(id);
		if(!hasAccess(collabRating)) {
			return null;
		}
	    return collabRating;
	  }
	
	@GetMapping(value ="/{collabId}")
	 public Double calculateCollabRatingByCollaboratorId(@PathVariable int collabId) {
		return collabRatingService.getCollaboratorRatingById(collabId);
	  }
	
	@PutMapping(value = "/")
	public CollabRating updateCollabRating(@RequestBody CollabRating collabRating)  {
		CollabRating oldCollabRating = collabRatingService.getCollabRatingById(collabRating.getId());
		if(!hasAccess(oldCollabRating)) {
			return null;
		}
	  return collabRatingService.updateCollabRating(collabRating);
	}
	
	private static boolean hasAccess(CollabRating collabRating) {
		UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
		if(roles.contains("ROLE_ADMIN")) {return true;}
		if(userDetails.getId()==collabRating.getUser().getId()) {return true;}
		return false;
	}
}
