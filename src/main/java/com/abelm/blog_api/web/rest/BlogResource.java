package com.abelm.blog_api.web.rest;

import com.abelm.blog_api.service.BlogService;
import com.abelm.blog_api.service.dto.BlogDTO;
import com.abelm.blog_api.service.dto.BlogDetailDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
public class BlogResource {
    private final BlogService blogService;

    @PostMapping()
    public ResponseEntity<BlogDTO> createBlog(@Valid @RequestBody BlogDTO blogRequest) {
        log.debug("REST request to create blog: {}", blogRequest);
        BlogDTO response = blogService.createBlog(blogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public ResponseEntity<BlogDTO> updateBlog(@Valid @RequestBody BlogDTO blogRequest){
        log.debug("REST request to update blog: {}", blogRequest);
        BlogDTO response = blogService.updateBlog(blogRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDetailDTO> getBlogByID(@PathVariable Long id){
        log.debug("REST request to get a blog: {}", id);
        BlogDetailDTO response = blogService.findBlogById(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogById(@PathVariable Long id){
        log.debug("REST request to delete blog: {}", id);
        blogService.deleteBlogById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<BlogDetailDTO>> getAllBlog(){
        List<BlogDetailDTO> response = blogService.findAllBlogs();
        return ResponseEntity.ok().body(response);
    }
}
