package com.abelm.blog_api.repository;

import com.abelm.blog_api.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
