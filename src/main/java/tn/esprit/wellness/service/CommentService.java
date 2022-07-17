package tn.esprit.wellness.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.entity.utils.PagingComment;


public interface CommentService {
	
	List<Comment> retrieveAllComment();
	Comment addComment(Comment comment);
	void deleteCommenteById(int id);
	void deleteComment(Comment comment);
	Comment updateComment(Comment comment);
	Comment retrieveCommentById(int id);
	Comment increLike(int id, boolean b);
	PagingComment get(Specification<Comment> spec, HttpHeaders headers, Sort sort);

}
