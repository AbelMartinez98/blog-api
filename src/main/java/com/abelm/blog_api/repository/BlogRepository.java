package com.abelm.blog_api.repository;

import com.abelm.blog_api.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Optional<Blog> findByIdAndActiveTrue(Long id);

    List<Blog> findAllByActiveTrue();
}
