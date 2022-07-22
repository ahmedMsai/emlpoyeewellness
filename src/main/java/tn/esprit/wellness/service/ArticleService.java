package tn.esprit.wellness.service;

import java.util.List;
import java.util.Optional;

import tn.esprit.wellness.entity.Article;

public interface ArticleService {
    
	Article getArticleById(int id);
	List<Article> getAllArticles();
	int addArticle (Article article);
	void deleteArticleById (int id);
	Article updateArticle (Article article);
	
}
