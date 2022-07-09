package tn.esprit.wellness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article , Integer >{
	@Query("SELECT p FROM Article p WHERE p.id=:id LIMIT 1")
	public Article findById(@Param("id")String id);
}
