package tn.esprit.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.service.AnswerService;

import tn.esprit.wellness.entity.PossibleAnswer;


@RequestMapping("api/v1/answer")
@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping()
    public ResponseEntity<List<PossibleAnswer>> getAllAnswers(){
        return ResponseEntity.ok().body(answerService.getAllAnswer());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<PossibleAnswer> getAnswerById(@PathVariable long id){
        return ResponseEntity.ok().body(answerService.getAnswerById(id));
    }

    @PostMapping()
    public ResponseEntity<PossibleAnswer> createAnswer(@RequestBody PossibleAnswer possibleAnswer){
        return ResponseEntity.ok().body(answerService.createAnswer(possibleAnswer));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<PossibleAnswer> updateAnswer(@PathVariable long id, @RequestBody PossibleAnswer possibleAnswer){
        return ResponseEntity.ok().body(this.answerService.updateAnswer(id, possibleAnswer));
    }

    @DeleteMapping(path = "{id}")
    public HttpStatus deleteAnswer(@PathVariable long id){
        this.answerService.deleteAnswer(id);
        return HttpStatus.OK;
    }

}
