
package tn.esprit.wellness.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;



import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.service.CommentService;
import tn.esprit.wellness.entity.utils.AppConstants;
import tn.esprit.wellness.entity.utils.ForbiddenWords;
import tn.esprit.wellness.entity.utils.PagingComment;
import tn.esprit.wellness.entity.utils.PagingHeaders;
import tn.esprit.wellness.payload.CommentPayload;



@RestController
@RequestMapping(value = "comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	/*
	@PostMapping(value = "/addComment")
	public Comment addComment(@RequestBody Comment c) { 
		return commentService.addComment(c);
	}*/
	
	

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
	 
		@PostMapping(value = "/addComment")
		public Comment addComment(@RequestBody Comment c) { 
			String[] words = c.getContent().split(" ");
			for(int i = 0;i< words.length;i++){
			if(ForbiddenWords.forbiddenWords.contains(words[i].trim())){
				c.setContent(c.getContent().replace(words[i], "*****"));
			}
			}
			return commentService.addComment(c);
		}
		
	    @GetMapping(value = "/allComments")
	    public CommentPayload getAllPosts(
	            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
	            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
	            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
	            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
	    ){
	        return commentService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	    }
	
}
