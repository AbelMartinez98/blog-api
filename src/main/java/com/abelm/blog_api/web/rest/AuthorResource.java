package com.abelm.blog_api.web.rest;

import com.abelm.blog_api.service.AuthorService;
import com.abelm.blog_api.service.dto.AuthorDTO;
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
@RequestMapping("/api/author")
public class AuthorResource {
    private final AuthorService authorService;

    @PostMapping()
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorRequest) {
        log.debug("REST request to create author: {}", authorRequest);
        AuthorDTO response = authorService.createAuthor(authorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public ResponseEntity<AuthorDTO> updateAuthor(@Valid @RequestBody AuthorDTO authorRequest){
        log.debug("REST request to update author: {}", authorRequest);
        AuthorDTO response = authorService.updateAuthor(authorRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorByID(@PathVariable Long id){
        log.debug("REST request to get a author: {}", id);
        AuthorDTO response = authorService.findAuthorById(id);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id){
        log.debug("REST request to delete author: {}", id);
        authorService.deleteAuthorById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthor(){
        List<AuthorDTO> response = authorService.findAllAuthor();
        return ResponseEntity.ok().body(response);
    }
}
