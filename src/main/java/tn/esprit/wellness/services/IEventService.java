package tn.esprit.wellness.services;

import java.util.Date;
import java.util.List;
import tn.esprit.wellness.entity.Event;

public interface IEventService {

	public void addEvent(Event event);

	public void deleteEventById(int eventId);

	public Event getEventById(int id);

	public List<Event> getEventByDepartement(String depId);

	public List<Event> getEventByBureau(String bId);

	public List<Event> getAllEvents();

	public void updateLabelEvent(String label, int eventId);

	public void updateActivityEvent(String activity, int eventId);

	public void updateDateEvent(Date date, int eventId);

	public void participateEvent(int userId, int eventId);

	public void inviteCoworkerForEvent(int userId, int eventId);
	
	public void inviteCoworkers(int eventId);
	
	public void inviteCoworkersFromDepartment(int eventId, String depId);

	public void acceptInvitation(int userId, int eventId);

	public void refuseInvitation(int userId, int eventId);

	public void noteCommentaire(int userId, int eventId, String commentaire, int note, Date date);
	
	public void generateStat();

}
