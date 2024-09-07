package com.abelm.blog_api.service;

import com.abelm.blog_api.domain.Author;
import com.abelm.blog_api.domain.Blog;
import com.abelm.blog_api.domain.Comment;
import com.abelm.blog_api.repository.AuthorRepository;
import com.abelm.blog_api.repository.BlogRepository;
import com.abelm.blog_api.service.dto.*;
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
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthorRepository authorRepository;

    /**
     * Creates a new Blog
     *
     * @param request the request object containing the blog information
     * @return a BlogDTO object representing the newly created Blog
     */
    @Transactional
    public BlogDTO createBlog(BlogDTO request) {
        Author author = authorRepository.findByIdAndActiveTrue(request.getAuthor().getId())
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_AUTHOR_NOT_FOUND,
                        ErrorMessages.ERROR_AUTOR_NO_ENCONTRADO
                ));

        Blog entity = Blog.builder()
                .title(request.getTitle())
                .topic(request.getTopic())
                .content(request.getContent())
                .frequency(request.getFrequency())
                .allowComments(request.getAllowComments())
                .active(true)
                .author(author)
                .build();

        blogRepository.save(entity);
        log.info("Created new blog with id {}", entity.getId());
        return new BlogDTO(entity);
    }

    /**
     * Updates an existing Blog
     *
     * @param request the request object containing the updated blog information
     * @return a BlogDTO object representing the updated blog
     * @throws NotFoundAlertException if the blog does not exist
     */
    @Transactional
    public BlogDTO updateBlog(BlogDTO request) {
        Blog entity = blogRepository.findByIdAndActiveTrue(request.getId())
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_BLOG_NOT_FOUND,
                        ErrorMessages.ERROR_BLOG_NO_ENCONTRADO
                ));

        entity.setTitle(request.getTitle());
        entity.setTopic(request.getTopic());
        entity.setContent(request.getContent());
        entity.setFrequency(request.getFrequency());
        entity.setAllowComments(request.getAllowComments());
        entity.setActive(request.getActive());

        blogRepository.save(entity);
        log.info("Updated blog with id {}", entity.getId());
        return new BlogDTO(entity);
    }

    /**
     * Finds a blog by its ID
     *
     * @param id of the blog to be found
     * @return the found BlogDTO
     * @throws NotFoundAlertException if the blog does not exist
     */
    @Transactional(readOnly = true)
    public BlogDetailDTO findBlogById(Long id) {
        Blog entity = blogRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_BLOG_NOT_FOUND,
                        ErrorMessages.ERROR_BLOG_NO_ENCONTRADO
                ));

        log.debug("Found blog : {}", entity);

        // Calcular resumen de puntuaciones
        RatingSummaryDTO ratingSummary = calculateRatingSummary(entity);

        return new BlogDetailDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getTopic(),
                entity.getContent(),
                entity.getFrequency(),
                entity.getAllowComments(),
                entity.getActive(),
                new AuthorDTO(entity.getAuthor()),
                entity.getComments().stream().map(CommentDTO::new).collect(Collectors.toList()),
                ratingSummary
        );
    }


    /**
     * Deletes a blog by its ID
     *
     * @param id of the blog to be deleted
     * @throws NotFoundAlertException if the blog does not exist
     */
    @Transactional
    public void deleteBlogById(Long id) {
        Blog entity = blogRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_BLOG_NOT_FOUND,
                        ErrorMessages.ERROR_BLOG_NO_ENCONTRADO
                ));

        entity.setActive(false);
        blogRepository.save(entity);
        log.info("Deleted blog with id {}", entity.getId());
    }

    /**
     * Finds all blogs
     *
     * @return a list of all blogs
     */
    @Transactional(readOnly = true)
    public List<BlogDetailDTO> findAllBlogs() {
        return blogRepository.findAllByActiveTrue().stream()
                .map(blog -> new BlogDetailDTO(
                        blog.getId(),
                        blog.getTitle(),
                        blog.getTopic(),
                        blog.getContent(),
                        blog.getFrequency(),
                        blog.getAllowComments(),
                        blog.getActive(),
                        new AuthorDTO(blog.getAuthor()),
                        blog.getComments().stream().map(CommentDTO::new).collect(Collectors.toList()),
                        calculateRatingSummary(blog) // Calcula el resumen de puntuaciones
                ))
                .collect(Collectors.toList());
    }


    private RatingSummaryDTO calculateRatingSummary(Blog blog) {
        List<Integer> ratings = blog.getComments().stream()
                .map(Comment::getRating)
                .toList();

        if (ratings.isEmpty()) {
            // Devuelvo valores por defecto si no hay puntuaciones
            return new RatingSummaryDTO(0, 0, 0.0);
        }

        int minRating = ratings.stream().min(Integer::compare).orElse(0);
        int maxRating = ratings.stream().max(Integer::compare).orElse(0);
        double avgRating = ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        return new RatingSummaryDTO(minRating, maxRating, avgRating);
    }

}