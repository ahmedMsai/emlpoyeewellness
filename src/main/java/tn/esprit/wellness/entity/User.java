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

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String lastname;
  @JsonIgnore
  private Date birthday;
  @Enumerated(EnumType.STRING)
	private Genre genre;
  @JsonIgnore
  private String department;
  private Long points;
	private String bureau;
  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  @JsonIgnore
  @NotBlank
  @Size(max = 120)
  private String password;
  @JsonIgnore
  private String imgUrl;
  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Article> articles = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = { CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Event> events = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = { CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Badge> badges = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = { CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Reservation> reservations = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = { CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = { CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<CollabRating> collabRatings = new ArrayList<>();
  @ManyToMany
	@JoinTable(name = "participation", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventss = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "invitation", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventsss = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "notecommentaire", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idEvent"))
	private List<Event> eventssss = new ArrayList<>();
  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
