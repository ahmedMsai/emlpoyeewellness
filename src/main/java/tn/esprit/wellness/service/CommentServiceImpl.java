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

import tn.esprit.wellness.entity.Comment;
import tn.esprit.wellness.entity.utils.PagingComment;
import tn.esprit.wellness.entity.utils.PagingHeaders;
import tn.esprit.wellness.repository.ArticleRepository;
import tn.esprit.wellness.repository.CommentRepository;

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
	
	/**
     * get element using Criteria.
     *
     * @param spec    *
     * @param headers pagination data
     * @param sort    sort criteria
     * @return retrieve elements with pagination
     */
	@Override
    public PagingComment get(Specification<Comment> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<Comment> entities = get(spec, sort);
            return new PagingComment((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }
    
    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }

    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
    
    /**
     * get elements using Criteria.
     *
     * @param spec *
     * @return elements
     */
    public List<Comment> get(Specification<Comment> spec, Sort sort) {
        return commentRepository.findAll(spec, sort);
    }
    
    /**
     * get elements using Criteria.
     *
     * @param spec     *
     * @param pageable pagination data
     * @return retrieve elements with pagination
     */
    public PagingComment get(Specification<Comment> spec, Pageable pageable) {
        Page<Comment> page = commentRepository.findAll(spec, pageable);
        List<Comment> content = page.getContent();
        return new PagingComment(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }


}