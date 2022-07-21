package tn.esprit.wellness.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.wellness.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, PagingAndSortingRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {


}
