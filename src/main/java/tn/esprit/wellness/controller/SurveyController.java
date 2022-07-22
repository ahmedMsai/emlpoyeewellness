package tn.esprit.wellness.controller;

import tn.esprit.wellness.entity.Survey;
import tn.esprit.wellness.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("/survey")
@Controller
public class SurveyController {

    @Autowired
    private SurveyService surveyService;
    
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping(path = "/allSurveys")
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok().body(surveyService.getAllSurvey());
    }

	@GetMapping(value = "/findSurvey/{id}")
	public ResponseEntity getSurveyById(@PathVariable(value = "id") Long id) {
		Survey survey = null;
		try {
			survey = surveyService.getSurveyById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(survey);
	}
    @PostMapping
    public ResponseEntity<Survey> createSurvey (@RequestBody Survey survey) {
        MimeMessagePreparator preparator =  mimeMessage -> {
            String[] to =  {"rania.benkhalil@esprit.tn"};

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
            message.setTo(to);
            message.setSubject("A new survey is available");
            message.setFrom("dragunovcsgo@gmail.com");
            String surveyId = "http://localhost:8083/survey/findSurvey/" + survey.getId();
            message.setText("Hello , please check <h2><a href=${surveyId}>A new survey is available</a></h1>", true);
      
        };
       mailSender.send(preparator);
        return ResponseEntity.ok().body(surveyService.createSurvey(survey));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateSurvey(@PathVariable("id") Long id, @Valid @NotNull @RequestBody Survey survey) {
		try {
			survey = surveyService.getSurveyById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(surveyService.updateSurvey(id, survey));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteSurveyById(@PathVariable("id") Long id) {
    	try {
    		surveyService.deleteSurvey(id);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Survey Deleted!");
    }
    
}
