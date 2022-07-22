package tn.esprit.wellness.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String label;
	private Date date;
	@Enumerated(EnumType.STRING)
	private Theme theme;
	private String activity;
	@OneToMany(mappedBy = "event", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<Reservation> reservations = new ArrayList<>();
	@ManyToOne()
	private User user;
	@ManyToMany
	@JoinTable(name = "participation", joinColumns = @JoinColumn(name = "idEvent"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private List<User> users = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "invitation", joinColumns = @JoinColumn(name = "idEvent"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private List<User> userss = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "notecommentaire", joinColumns = @JoinColumn(name = "idEvent"), inverseJoinColumns = @JoinColumn(name = "idUser"))
	private List<User> usersss = new ArrayList<>();

	public Event() {
		super();

	}

	public Event(int id, String label, Date date, Theme theme, String activity, List<Reservation> reservations,
			User user) {
		super();
		this.id = id;
		this.label = label;
		this.date = date;
		this.theme = theme;
		this.activity = activity;
		this.reservations = reservations;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", label=" + label + ", date=" + date + ", theme=" + theme + ", activity=" + activity
				+ ", reservations=" + reservations + ", user=" + user + "]";
	}

}
