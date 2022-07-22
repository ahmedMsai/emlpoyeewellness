package tn.esprit.wellness.repository;

import tn.esprit.wellness.entity.PossibleAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository <PossibleAnswer, Long> {

}
