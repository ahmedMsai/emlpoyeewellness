package tn.esprit.wellness.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.wellness.entity.Event;
import tn.esprit.wellness.repository.EventRepository;

@Service
public class EventServiceImpl implements IEventService {

	@Autowired
	EventRepository eventRepository;

	@Override
	public void addEvent(Event event) {
		eventRepository.save(event);

	}

	@Override
	public void deleteEventById(int eventId) {
		eventRepository.delete(eventRepository.findById(eventId).orElse(null));

	}

	@Override
	public List<Event> getAllEvents() {
		return (List<Event>) eventRepository.findAll();
	}

	@Override
	public Event getEventById(int id) {

		return eventRepository.findById(id).orElse(null);
	}

	@Override
	public List<Event> getEventByDepartement(String depId) {

		if (eventRepository.findByDepId(depId) != null) {
			return eventRepository.findByDepId(depId);
		} else {
			return Collections.emptyList();
		}

	}

	@Override
	public List<Event> getEventByBureau(String bId) {

		if (eventRepository.findByBureauId(bId) != null) {
			return eventRepository.findByBureauId(bId);
		} else {
			return Collections.emptyList();
		}

	}

	@Override
	public void updateLabelEvent(String label, int eventId) {
		try {

			Event event = eventRepository.findById(eventId).orElse(null);
			if (event != null) {
				event.setLabel(label);
				eventRepository.save(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateActivityEvent(String activity, int eventId) {
		try {

			Event event = eventRepository.findById(eventId).orElse(null);
			if (event != null) {
				event.setActivity(activity);
				eventRepository.save(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateDateEvent(Date date, int eventId) {
		try {

			Event event = eventRepository.findById(eventId).orElse(null);
			if (event != null) {
				event.setDate(date);
				eventRepository.save(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void participateEvent(int userId, int eventId) {
		try {
			eventRepository.addParticipation(userId, eventId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void inviteCoworkerForEvent(int userId, int eventId) {
		try {
			eventRepository.inviteCoworker(userId, eventId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void acceptInvitation(int userId, int eventId) {
		try {
			eventRepository.acceptInvitation(userId, eventId);
			eventRepository.addParticipation(userId, eventId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void refuseInvitation(int userId, int eventId) {
		try {
			eventRepository.refuseInvitation(userId, eventId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void noteCommentaire(int userId, int eventId, String commentaire, int note, Date date) {
		try {
			eventRepository.noteCommentaire(userId, eventId, commentaire, note, date);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
