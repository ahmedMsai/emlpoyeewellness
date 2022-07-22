package tn.esprit.wellness.service;

import tn.esprit.wellness.exceptions.ResourceNotFoundException;
import tn.esprit.wellness.entity.Question;
import tn.esprit.wellness.entity.Survey;
import tn.esprit.wellness.repository.QuestionRepository;
import tn.esprit.wellness.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Survey createSurvey(Survey survey) {
        survey.setCreatorName(survey.getCreatorName());

        for(Question q : survey.getQuestionList()){
           if(questionRepository.findById(q.getId()).isPresent())
               questionRepository.findById(q.getId()).get().setSurvey(survey);
        }
        return surveyRepository.save(survey);
    }

    @Override
    public Survey updateSurvey(Long id, Survey survey) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            Survey surveyToUpdate = surveyDB.get();
            surveyToUpdate.setOpen(survey.isOpen());
            surveyToUpdate.setTitle(survey.getTitle());
            surveyToUpdate.setQuestionList(survey.getQuestionList());
            surveyRepository.save(surveyToUpdate);
            return surveyToUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + survey.getId());
        }
    }

    @Override
    public Survey getSurveyById(Long id) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            return surveyDB.get();
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + id);
        }
    }

    @Override
    public List<Survey> getAllSurvey() {
            return surveyRepository.findAllByOpenIsTrue();
    }

    @Override
    public void deleteSurvey(Long id) {
        Optional<Survey> surveyDB = this.surveyRepository.findById(id);

        if (surveyDB.isPresent()) {
            this.surveyRepository.delete(surveyDB.get());
        } else {
            throw new ResourceNotFoundException("Record not found with id:" + id);
        }
    }




}
