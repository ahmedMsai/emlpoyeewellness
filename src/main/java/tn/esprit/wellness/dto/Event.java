package tn.esprit.wellness.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.wellness.entity.Reservation;
import tn.esprit.wellness.entity.Theme;
import tn.esprit.wellness.entity.User;


@Getter
@Setter
@NoArgsConstructor
public class Event {
	
	private int id;
	private String label;
	private Date date;
	private Theme theme;
	private String activity;
	private List<Reservation> reservations = new ArrayList<>();
	private User user;
	

}
