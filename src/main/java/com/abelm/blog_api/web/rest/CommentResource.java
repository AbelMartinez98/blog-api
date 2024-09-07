package com.abelm.blog_api.web.rest;

import com.abelm.blog_api.service.CommentService;
import com.abelm.blog_api.service.dto.CommentDTO;
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
@RequestMapping("/api/comment")
public class CommentResource {
    private final CommentService commentService;

    @PostMapping("/blog/{blogId}")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long blogId,
                                                    @Valid @RequestBody CommentDTO commentRequest) {
        log.debug("REST request to create comment for blog ID {}: {}", blogId, commentRequest);
        CommentDTO response = commentService.createComment(blogId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<CommentDTO>> findCommentsByBlogId(@PathVariable Long blogId) {
        log.debug("REST request to find comments for blog ID {}", blogId);
        List<CommentDTO> comments = commentService.findCommentsByBlogId(blogId);
        return ResponseEntity.ok(comments);
    }
}
