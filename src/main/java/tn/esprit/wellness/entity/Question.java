package tn.esprit.wellness.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter@Entity(name="QUESTION")
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private long id;

    private String questionText;

    private boolean required;

    private String customAnswer;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne
    @JoinColumn(name = "done_survey_id")
    private DoneSurvey doneSurvey;

    @ManyToMany(mappedBy = "questionSet")
    private Set<PossibleAnswer> possibleAnswerSet = new HashSet<>();
    

}
