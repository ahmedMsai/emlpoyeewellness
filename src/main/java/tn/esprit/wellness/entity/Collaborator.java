package tn.esprit.wellness.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Collaborator implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String label;
	@JsonIgnore
	@OneToMany(mappedBy="collaborator", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Advertisement> advertisements = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy="collaborator", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<CollabRating> collabRatings = new ArrayList<>();
	@Transient
	@Getter(value = AccessLevel.NONE)
	private Double ratingScore;
	public Double getRatingScore() {
		return collabRatings.stream().mapToDouble(CollabRating::getRate)
			    .average()
			    .orElse(3);
	}
	
	@Transient
	@Getter(value = AccessLevel.NONE)
	private int nbrRatings;
	public int getNbrRatings() {
		return collabRatings.size();
	}
}
