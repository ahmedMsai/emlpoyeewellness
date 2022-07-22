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
@Setter
@Entity(name = "POSSIBLE_ANSWER")
public class PossibleAnswer {

    @Id
    @GeneratedValue
    private long id;
    private String text;


    @ManyToMany
    @JoinTable(
            name = "questions_possible_answers",
            joinColumns = @JoinColumn(name = "possible_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questionSet = new HashSet<>();

    public void addQuestion(Question question){
        questionSet.add(question);
    }
}
