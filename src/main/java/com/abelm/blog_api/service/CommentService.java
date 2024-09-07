package com.abelm.blog_api.service;

import com.abelm.blog_api.domain.Blog;
import com.abelm.blog_api.domain.Comment;
import com.abelm.blog_api.repository.BlogRepository;
import com.abelm.blog_api.repository.CommentRepository;
import com.abelm.blog_api.service.dto.CommentDTO;
import com.abelm.blog_api.service.exceptions.NotFoundAlertException;
import com.abelm.blog_api.web.rest.errors.ErrorKeys;
import com.abelm.blog_api.web.rest.errors.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    /**
     * Creates a new Comment for a Blog
     *
     * @param blogId the ID of the blog to comment on
     * @param request the CommentDTO object containing the comment information
     * @return a CommentDTO object representing the newly created comment
     * @throws NotFoundAlertException if the blog does not exist or comments are not allowed
     */
    @Transactional
    public CommentDTO createComment(Long blogId, CommentDTO request) {
        Blog blog = blogRepository.findByIdAndActiveTrue(blogId)
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_BLOG_NOT_FOUND,
                        ErrorMessages.ERROR_BLOG_NO_ENCONTRADO
                ));

        //Verifico si el blog permite comentarios
        if (!blog.getAllowComments()) {
            throw new NotFoundAlertException(
                    ErrorKeys.ERR_COMMENTS_NOT_ALLOWED,
                    ErrorMessages.ERROR_COMENTARIOS_NO_PERMITIDOS
            );
        }

        Comment comment = Comment.builder()
                .commenterName(request.getCommenterName())
                .commenterEmail(request.getCommenterEmail())
                .commenterCountry(request.getCommenterCountry())
                .content(request.getContent())
                .rating(request.getRating())
                .blog(blog)
                .build();

        commentRepository.save(comment);
        log.info("Created new comment with id {} for blog {}", comment.getId(), blog.getId());
        return new CommentDTO(comment);
    }

    /**
     * Finds comments by Blog ID
     *
     * @param blogId the ID of the blog to find comments for
     * @return a list of CommentDTOs associated with the blog
     * @throws NotFoundAlertException if the blog does not exist
     */
    @Transactional(readOnly = true)
    public List<CommentDTO> findCommentsByBlogId(Long blogId) {
        Blog blog = blogRepository.findByIdAndActiveTrue(blogId)
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_BLOG_NOT_FOUND,
                        ErrorMessages.ERROR_BLOG_NO_ENCONTRADO
                ));

        log.debug("Found comments for blog : {}", blog);
        return blog.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}
