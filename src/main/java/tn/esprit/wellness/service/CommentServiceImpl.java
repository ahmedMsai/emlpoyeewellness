package tn.esprit.wellness.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.entity.utils.PagingComment;
import tn.esprit.wellness.entity.utils.PagingHeaders;
import tn.esprit.wellness.payload.CommentPayload;
import tn.esprit.wellness.repository.ArticleRepository;
import tn.esprit.wellness.repository.CommentRepository;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	CommentRepository commentRepository ;
	
	@Autowired
	ArticleRepository articleRepository ;

	@Override
	public List<Comment> retrieveAllComment() {
		return commentRepository.findAll();
	}

	@Override
	public Comment addComment(Comment comment) {
		comment.setDate(LocalDateTime.now());
		return commentRepository.save(comment);
	}

	@Override
	public void deleteCommenteById(int id) {
		commentRepository.deleteById(id);
		
	}

	@Override
	public void deleteComment(Comment comment) {
		commentRepository.delete(comment);
		
	}

	@Override
	public Comment updateComment(Comment comment) {
		return commentRepository.save(comment);
	}


	@Override
	public Comment retrieveCommentById(int id) {
		return commentRepository.findById(id).get();
		}

	@Override
	public Comment increLike(int id, boolean incr) {
		Comment comment = commentRepository.findById(id).get();
		if(incr)
		comment.setNbLike(comment.getNbLike()+1);
		else
			comment.setNbLike(comment.getNbLike()-1);
		return commentRepository.save(comment);
	}

	  
	 @Override
	    public CommentPayload getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

		 Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
	                : Sort.by(sortBy).descending();
	               

	        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

	        Page<Comment> comments = commentRepository.findAll(pageable);

	        // get content for page object
	        List<Comment> listOfPosts = comments.getContent();

	        List<Comment> content = listOfPosts.stream().collect(Collectors.toList());

	        CommentPayload commentPayload = new CommentPayload();
	        commentPayload.setContent(content);
	        commentPayload.setPageNo(comments.getNumber());
	        commentPayload.setPageSize(comments.getSize());
	        commentPayload.setTotalElements(comments.getTotalElements());
	        commentPayload.setTotalPages(comments.getTotalPages());
	        commentPayload.setLast(comments.isLast());

	        return commentPayload;
	    }

	@Override
	public Comment createComment(Comment comment) {
        // convert DTO to entity
       
        Comment newComment = commentRepository.save(comment);

        return newComment;
	}
	


}