
package tn.esprit.wellness.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.service.CommentService;


@RestController
@RequestMapping(value = "comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	@PostMapping(value = "/addComment")
	public Comment addComment(@RequestBody Comment c) { 
		return commentService.addComment(c);
	}
	
	

	@DeleteMapping(value = "/deleteComment/{id}")
	void deleteCommentById(@PathVariable int id) {
		Comment comment = commentService.retrieveCommentById(id);
		if(LocalDateTime.now().minusSeconds(24L).isBefore(comment.getDate()))
		 commentService.deleteCommenteById(id);
	}
	
	@DeleteMapping(value = "/deleteComment")
	void deleteComment(@RequestBody Comment c) {
		 commentService.deleteComment(c);
	}
	
	 @GetMapping(value ="/showAll")
	 public List<Comment> retrieveAllComment() {
	    return commentService.retrieveAllComment();
	  }
	 
	 @GetMapping(value ="/showComment/{id}")
	 public Comment retriveCommentById(@PathVariable int id) {
	    return commentService.retrieveCommentById(id);
	  }
	 
	 @PutMapping(value = "/updateComment")
		public Comment updateComment(@RequestBody Comment c) {
		 Comment comment = commentService.retrieveCommentById(c.getId());
			if(LocalDateTime.now().minusSeconds(24L).isBefore(comment.getDate()))
			return commentService.updateComment(c);
			return null;
		}
	 
	 @GetMapping(value ="/likes/{id}")
	 public Comment incrementLike(@PathVariable int id) {
	    return commentService.increLike(id,true);
	  }
	 
	 @GetMapping(value ="/decrimentLikes/{id}")
	 public Comment decrimentlike(@PathVariable int id) {
	    return commentService.increLike(id,false);
	  }
	
}
