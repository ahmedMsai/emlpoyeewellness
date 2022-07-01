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
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String label;
	@OneToMany(mappedBy="offer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Collaboration> collaborations = new ArrayList<>();
}
