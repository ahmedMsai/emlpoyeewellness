package tn.esprit.wellness.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String lastname;
	private Date birthday;
	
	@Column(unique=true)
	@Pattern(regexp=".+[@].+[\\.].+")
	private String email;
	private Long points;
	private String bureau;
	private String department;
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Article> articles = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Event> events = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Badge> badges = new ArrayList<>();
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Reservation> reservations = new ArrayList<>();
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Collaboration> collaborations = new ArrayList<>();
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Article> publications = new ArrayList<>();
	@OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();
	
}
