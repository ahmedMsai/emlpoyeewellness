package tn.esprit.wellness.service;

import tn.esprit.wellness.entity.Survey;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface SurveyService {

        Survey createSurvey(Survey survey);
        Survey updateSurvey(Long id, Survey survey);
        Survey getSurveyById(Long id);
        List<Survey> getAllSurvey();
        void deleteSurvey(Long id);

}
