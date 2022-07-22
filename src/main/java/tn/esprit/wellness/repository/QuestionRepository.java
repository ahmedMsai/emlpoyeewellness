package tn.esprit.wellness.repository;

import tn.esprit.wellness.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
