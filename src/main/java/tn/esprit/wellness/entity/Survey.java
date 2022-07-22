package tn.esprit.wellness.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="SURVEY")
public class Survey {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    private String title;

    private boolean open;

    private String creatorName;
	@ManyToOne()
	private User user;
	
    @OneToMany(mappedBy = "survey")
    private Set<Question> questionList =  new HashSet<>();

}
