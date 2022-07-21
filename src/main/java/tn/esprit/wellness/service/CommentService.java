package tn.esprit.wellness.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.entity.utils.PagingComment;
import tn.esprit.wellness.payload.CommentPayload;


public interface CommentService {
	
	List<Comment> retrieveAllComment();
	Comment addComment(Comment comment);
	void deleteCommenteById(int id);
	void deleteComment(Comment comment);
	Comment updateComment(Comment comment);
	Comment retrieveCommentById(int id);
	Comment increLike(int id, boolean b);
	CommentPayload getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
//    Comment createPost(PostDto postDto);
	//PagingComment get(Specification<Comment> spec, HttpHeaders headers, Sort sort);
	Comment createComment(Comment comment);

}
