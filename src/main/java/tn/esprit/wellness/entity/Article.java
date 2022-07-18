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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Article implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String label;
	private Date date;
	@Enumerated(EnumType.STRING)
	private Theme theme;
	@OneToMany(mappedBy="article", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();
	@ManyToOne()
	private User user;
}
