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
import tn.esprit.wellness.security.services.UserDetailsImpl;
import tn.esprit.wellness.service.CollaboratorService;

@RestController
@RequestMapping(value = "/api/auth/collaborator")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public class CollaboratorController {

	@Autowired
	private CollaboratorService collaboratorService;

	@PostMapping(value = "/")
	public Collaborator addCollaborator(@RequestBody Collaborator collaborator) {
		return collaboratorService.addCollaborator(collaborator);
	}
	
	@GetMapping(value = "/all")
	public List<Collaborator> getAllCollaborators(){
		return collaboratorService.getAllCollaborators();
	}
	
	@DeleteMapping(value = "/{id}")
	ResponseEntity<String> deleteCollaborator(@PathVariable int id ) {
		
		try {
			Collaborator collaborator = collaboratorService.getCollaboratorById(id);
			try {
				collaboratorService.deleteCollaboratorById(id);
				return ResponseEntity.status(HttpStatus.OK).body("Collaborator '" + collaborator.getLabel() + "' was deleted successfully");
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Collaborator '" + collaborator.getLabel() + "' could not be deleted");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Collaborator with id '" + id + "' not found");
		}
		 
	}

	@GetMapping(value ="/{id}")
	 public Collaborator retriveCollaborator(@PathVariable int id) {
		
	    return collaboratorService.getCollaboratorById(id);
	  }
	
	@PutMapping(value = "/")
	public Collaborator updateCollaborator(@RequestBody Collaborator collaborator)  {
	  return collaboratorService.updateCollaborator(collaborator);
	}
	
	
}
