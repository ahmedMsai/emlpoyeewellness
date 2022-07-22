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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Offer implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@NotNull
	private String label;
	@NotNull
	private String about;
	@Transient
	@Getter(value = AccessLevel.NONE)
	private OfferStatus status;
	@NotNull
	@Enumerated(EnumType.STRING)
	private OfferType type;
	@NotNull
	private Date expireBy;
	@NotNull
	private Date startingFrom;
	@NotNull
	@ManyToOne()
	private Advertisement advertisement;
	
	public OfferStatus getStatus() {
		Date now = new Date();
		if(now.after(expireBy)) {
			return OfferStatus.EXPIRED;
		}
		if(now.before(startingFrom)) {
			return OfferStatus.DRAFT;
		}
		return OfferStatus.ACTIVE;
	}
}
