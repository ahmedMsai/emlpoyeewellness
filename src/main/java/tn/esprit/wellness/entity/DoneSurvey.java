package tn.esprit.wellness.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "DONE_SURVEY")
public class DoneSurvey {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String creatorName;

    private String respondentName;

    private String questionText;

    private String givenAnswer;


}
