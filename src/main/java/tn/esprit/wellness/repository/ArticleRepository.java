package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article , Integer>{
	
}
