package tn.esprit.wellness.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.wellness.entity.Event;
import tn.esprit.wellness.services.IEventService;

@RestController
public class ControllerEvent {

	@Autowired
	IEventService ieventService;

	// http://localhost:8081/SpringMVC/servlet/addEvent
	@PostMapping("/addEvent")
	@ResponseBody
	public Event addEvent(@RequestBody tn.esprit.wellness.dto.Event event) {
		Event eventEntity = new Event();
		eventEntity.setId(event.getId());
		eventEntity.setLabel(event.getLabel());
		eventEntity.setDate(event.getDate());
		eventEntity.setTheme(event.getTheme());
		eventEntity.setActivity(event.getActivity());
		eventEntity.setReservations(event.getReservations());
		eventEntity.setUser(event.getUser());
		ieventService.addEvent(eventEntity);
		return eventEntity;
	}

	// http://localhost:8081/SpringMVC/servlet/deleteEventById/8
	@DeleteMapping("/deleteEventById/{idevent}")
	@ResponseBody
	public void deleteEventById(@PathVariable("idevent") int eventId) {
		ieventService.deleteEventById(eventId);

	}

	// http://localhost:8081/SpringMVC/servlet/getAllEvents
	@GetMapping(value = "/getAllEvents")
	@ResponseBody
	public List<Event> getAllEvents() {

		return ieventService.getAllEvents();
	}
	
	// http://localhost:8081/SpringMVC/servlet/getEventById/2
	@GetMapping(value = "getEventById/{idevent}")
	@ResponseBody
	public Event getEventById(@PathVariable("idevent") int eventId) {

		return ieventService.getEventById(eventId);
	}

	// http://localhost:8081/SpringMVC/servlet/getEventByDepartement/d1
	@GetMapping(value = "getEventByDepartement/{idDep}")
	@ResponseBody
	public List<Event> getEventByDepartement(@PathVariable("idDep") String idDep) {

		return ieventService.getEventByDepartement(idDep);
	}

	// http://localhost:8081/SpringMVC/servlet/getEventByBureau/b1
	@GetMapping(value = "getEventByBureau/{bId}")
	@ResponseBody
	public List<Event> getEventByBureau(@PathVariable("bId") String bId) {

		return ieventService.getEventByBureau(bId);
	}

	// Update Label:
	// http://localhost:8081/SpringMVC/servlet/updateEventLabel/2/newLabel
	@PutMapping(value = "/updateEventLabel/{id}/{newLabel}")
	@ResponseBody
	public void updateLabelEvent(@PathVariable("newLabel") String label, @PathVariable("id") int eventId) {
		ieventService.updateLabelEvent(label, eventId);

	}

	// Update Activity :
	// http://localhost:8081/SpringMVC/servlet/updateEventActivity/2/newActivity
	@PutMapping(value = "/updateEventActivity/{id}/{newActivity}")
	@ResponseBody
	public void updateActivityEvent(@PathVariable("newActivity") String activity, @PathVariable("id") int eventId) {
		ieventService.updateActivityEvent(activity, eventId);

	}

	// Update Date :
	// http://localhost:8081/SpringMVC/servlet/updateEventDate/2/newDate
	@PutMapping(value = "/updateEventDate/{idEvent}/{newDate}")
	@ResponseBody
	public void updateDateEvent(
			@PathVariable("newDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZZZ") Date date,
			@PathVariable("idEvent") int eventId) {
		ieventService.updateDateEvent(date, eventId);

	}

	// participate event
	// http://localhost:8081/SpringMVC/servlet/participate/1/2
	@PostMapping("/participate/{idUser}/{idEvent}")
	@ResponseBody
	public void participateEvent(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId) {

		ieventService.participateEvent(userId, eventId);

	}

	// invite 1 coworker for event
	// http://localhost:8081/SpringMVC/servlet/invite/1/2
	@PostMapping("/invite/{idUser}/{idEvent}")
	@ResponseBody
	public void inviteCoworkerForEvent(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId) {

		ieventService.inviteCoworkerForEvent(userId, eventId);

	}

	// invite many coworkers for event
	// http://localhost:8081/SpringMVC/servlet/inviteCoworkers/2
	@PostMapping("/inviteCoworkers/{idEvent}")
	@ResponseBody
	public void inviteCoworkers(@PathVariable("idEvent") int eventId) {

		ieventService.inviteCoworkers(eventId);

	}

	// invite many coworkers for event
	// http://localhost:8081/SpringMVC/servlet/inviteCoworkersFromDepartement/2/d1
	@PostMapping("/inviteCoworkersFromDepartement/{idEvent}/{idDep}")
	@ResponseBody
	public void inviteCoworkersFromDepartement(@PathVariable("idEvent") int eventId,@PathVariable("idDep") String depId) {

		ieventService.inviteCoworkersFromDepartment(eventId, depId);

	}

	// accept invitation for an event :
	// http://localhost:8081/SpringMVC/servlet/acceptInvitation/1/2
	@PutMapping(value = "/acceptInvitation/{idUser}/{idEvent}")
	@ResponseBody
	public void acceptInvitation(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId) {
		ieventService.acceptInvitation(userId, eventId);

	}

	// refuse invitation for an event :
	// http://localhost:8081/SpringMVC/servlet/refuseInvitation/1/2
	@PutMapping(value = "/refuseInvitation/{idUser}/{idEvent}")
	@ResponseBody
	public void refuseInvitation(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId) {
		ieventService.refuseInvitation(userId, eventId);

	}

	// note and comment an event

	// http://localhost:8081/SpringMVC/servlet/noteCommentaire/1/2/Good !/4 ...
	@PostMapping("/noteCommentaire/{idUser}/{idEvent}/{commentaire}/{note}")
	@ResponseBody
	public void notecommentaire(@PathVariable("idUser") int userId, @PathVariable("idEvent") int eventId,
			@PathVariable("commentaire") String commentaire, @PathVariable("note") int note) {

		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();
		date.setHours(date.getHours() + 1);

		ieventService.noteCommentaire(userId, eventId, commentaire, note, date);

	}

	// http://localhost:8081/SpringMVC/servlet/generateStat
	@PutMapping(value = "/generateStat")
	@ResponseBody
	public void generateStat() {
		ieventService.generateStat();

	}
}
