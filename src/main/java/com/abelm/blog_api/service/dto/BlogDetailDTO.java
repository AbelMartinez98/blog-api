package com.abelm.blog_api.service.dto;

import com.abelm.blog_api.domain.enumeration.FrequencyBlogEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDetailDTO {
    private Long id;
    private String title;
    private String topic;
    private String content;
    private FrequencyBlogEnum frequency;
    private Boolean allowComments;
    private Boolean active;
    private AuthorDTO author;
    private List<CommentDTO> comments;
    private RatingSummaryDTO ratingSummary;
}
