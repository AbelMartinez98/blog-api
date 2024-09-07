package com.abelm.blog_api.repository;

import com.abelm.blog_api.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findOneByEmailIgnoreCase(String email);
    Optional<Author> findByIdAndActiveTrue(Long id);

    List<Author> findAllByActiveTrue();
}
