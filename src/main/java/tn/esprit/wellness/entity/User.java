package tn.esprit.wellness.entity;

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
  @JsonIgnore
  private String department;

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
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Event> events = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Badge> badges = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Reservation> reservations = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();
  @JsonIgnore
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<CollabRating> collabRatings = new ArrayList<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}