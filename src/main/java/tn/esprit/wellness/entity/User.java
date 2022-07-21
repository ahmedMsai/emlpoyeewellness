package tn.esprit.wellness.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

  private Date birthday;

  private String department;

  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private String imgUrl;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

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
  private List<Collaborator> collaborations = new ArrayList<>();
  @OneToMany(mappedBy="user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
          fetch=FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}