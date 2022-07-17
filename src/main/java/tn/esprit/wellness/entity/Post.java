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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String label;
	private String content;
	@OneToMany(mappedBy="post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, 
			fetch=FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();
	@ManyToOne()
	private User user;
}
