package tn.esprit.wellness.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stat {
	
	
	@Id
	private Theme theme;
	private float moyenne;
	
	
	public Stat() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Stat(Theme theme, int moyenne) {
		super();
		this.theme = theme;
		this.moyenne = moyenne;
	}


	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public float getMoyenne() {
		return moyenne;
	}
	public void setMoyenne(float moyenne) {
		this.moyenne = moyenne;
	}

	
}
