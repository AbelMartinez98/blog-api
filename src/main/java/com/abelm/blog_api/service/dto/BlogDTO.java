package com.abelm.blog_api.service.dto;

import com.abelm.blog_api.domain.Blog;
import com.abelm.blog_api.domain.enumeration.FrequencyBlogEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String topic;
    private String content;
    private FrequencyBlogEnum frequency;
    private Boolean allowComments;
    private Boolean active;
    private AuthorDTO author;
    private List<CommentDTO> comments;

    public BlogDTO(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.topic = blog.getTopic();
        this.content = blog.getContent();
        this.frequency = blog.getFrequency();
        this.allowComments = blog.getAllowComments();
        this.active = blog.getActive();
        this.author = new AuthorDTO(blog.getAuthor());
        this.comments = blog.getComments().stream().map(CommentDTO::new).toList();
    }
}
