package tn.esprit.wellness.repository;

import tn.esprit.wellness.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByCreatorName(String creatorName);
    List<Survey> findAllByOpenIsTrue();
}
