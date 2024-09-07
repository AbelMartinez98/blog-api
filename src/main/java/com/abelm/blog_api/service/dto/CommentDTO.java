package com.abelm.blog_api.service.dto;

import com.abelm.blog_api.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String commenterName;
    private String commenterEmail;
    private String commenterCountry;
    private String content;
    private int rating;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.commenterName = comment.getCommenterName();
        this.commenterEmail = comment.getCommenterEmail();
        this.commenterCountry = comment.getCommenterCountry();
        this.content = comment.getContent();
        this.rating = comment.getRating();
    }
}
