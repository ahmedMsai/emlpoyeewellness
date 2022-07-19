package tn.esprit.wellness.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String lastname;
	private Date birthday;

	@Column(unique = true)
	@Pattern(regexp = ".+[@].+[\\.].+")
	private String email;
	private Long points;
	private String bureau;
	private String department;
	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany
	private List<Article> articles = new ArrayList<>();

	@OneToMany
	private List<Event> events = new ArrayList<>();

	@OneToMany
	private List<Badge> badges = new ArrayList<>();

	@OneToMany
	private List<Reservation> reservations = new ArrayList<>();
	@OneToMany
	private List<Collaboration> collaborations = new ArrayList<>();
	@OneToMany
	private List<Post> publications = new ArrayList<>();
	@OneToMany
	private List<Comment> comments = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "participation", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventss = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "invitation", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventsss = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "note_commentaire", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventssss = new ArrayList<>();

}
