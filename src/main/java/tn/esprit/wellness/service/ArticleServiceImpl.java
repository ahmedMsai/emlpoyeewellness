package tn.esprit.wellness.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.Article;
import tn.esprit.wellness.repository.ArticleRepository;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	ArticleRepository articleRepository;

	@Override
	public int addArticle(Article article) {
		return articleRepository.save(article).getId();
	}

	@Override
	public void deleteArticleById(int id) {
       articleRepository.deleteById(id);	
	}

	@Override
	public Article updateArticle(Article article) {
		if (articleRepository.findById(article.getId())==null) {
			return null;	
		}	
		return articleRepository.save(article);
	}

	@Override
	public Optional<Article> getArticleById(Article article) {
		return articleRepository.findById(article.getId());	
	}

	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

}
