package tn.esprit.wellness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.Article;
import tn.esprit.wellness.service.ArticleService;

@RestController
@RequestMapping(value = "/api/auth/article")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping(value = "/add")
	public int addArticle(@RequestBody Article a) {
		return articleService.addArticle(a);
	}
	
	@GetMapping(value = "/all")
	public List<Article> getAllArticles(){
		return articleService.getAllArticles();
	}
	
	@DeleteMapping(value = "/deleteArticle/{id}")
	ResponseEntity<String> deleteArticle(@PathVariable int id ) {
		try {
			articleService.deleteArticleById(id);
			} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body("Article " + id + " was deleted successfully");
		 
	}

	@GetMapping(value ="/getArticle/{id}")
	 public Article retriveSubject(@PathVariable int id) {
		
	    return articleService.getArticleById(id);
	  }
	
	@PutMapping(value = "/updateArticle")
	public Article updateArticle(@RequestBody Article a)  {
	  return articleService.updateArticle(a);
	}
	
}
