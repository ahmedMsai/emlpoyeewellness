package tn.esprit.wellness.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.wellness.entity.Article;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String content;
	private LocalDateTime date;
	private int nbLike;
	@ManyToOne()
	@JsonIgnore
	private User user;
	@ManyToOne()
	@JsonIgnore
	private Article article;
}
