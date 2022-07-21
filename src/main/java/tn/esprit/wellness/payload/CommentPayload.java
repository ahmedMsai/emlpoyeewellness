package tn.esprit.wellness.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.wellness.entity.Comment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPayload {
    private List<Comment> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}