package com.abelm.blog_api.service;

import com.abelm.blog_api.domain.Author;
import com.abelm.blog_api.repository.AuthorRepository;
import com.abelm.blog_api.service.dto.AuthorDTO;
import com.abelm.blog_api.service.exceptions.BadRequestAlertException;
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
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Creates a new Author
     *
     * @param request the request object containing the author information
     * @return an AuthorDTO object representing the new created Author
     */
    @Transactional
    public AuthorDTO createAuthor(AuthorDTO request){
        if (request.getEmail() != null) {
            if (authorRepository.findOneByEmailIgnoreCase(request.getEmail()).isPresent()) {
                throw new BadRequestAlertException(
                       ErrorKeys.ERR_EMAIL_ALREADY_EXIST, ErrorMessages.ERROR_EMAIL_EN_USO
                );
            }
        }
        Author entity = Author.builder()
                .firstName(request.getFirstName())
                .paternalSurname(request.getPaternalSurname())
                .maternalSurname(request.getMaternalSurname())
                .birthDate(request.getBirthDate())
                .residenceCountry(request.getResidenceCountry())
                .email(request.getEmail() != null ? request.getEmail().toLowerCase() : null)
                .active(true)
                .build();

        authorRepository.save(entity);
        log.info("Created new author with id {}", entity.getId());
        return new AuthorDTO(entity);
    }

    /**
     * Updates an existing author
     *
     * @param request the request object containing updated author information
     * @return a AuthorDTO object representing the updated author
     * @throws NotFoundAlertException if the author does not exist
     */
    @Transactional
    public AuthorDTO updateAuthor(AuthorDTO request){
        Author entity = authorRepository.findByIdAndActiveTrue(request.getId())
                .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_AUTHOR_NOT_FOUND,
                        ErrorMessages.ERROR_AUTOR_NO_ENCONTRADO
                ));

        if (request.getEmail() != null && !entity.getEmail().equalsIgnoreCase(request.getEmail())) {
            if (authorRepository.findOneByEmailIgnoreCase(request.getEmail()).isPresent()) {
                throw new BadRequestAlertException(
                        ErrorKeys.ERR_EMAIL_ALREADY_EXIST, ErrorMessages.ERROR_EMAIL_EN_USO
                );
            }
        }
        entity.setFirstName(request.getFirstName());
        entity.setPaternalSurname(request.getPaternalSurname());
        entity.setMaternalSurname(request.getMaternalSurname());
        entity.setBirthDate(request.getBirthDate());
        entity.setResidenceCountry(request.getResidenceCountry());
        entity.setEmail(request.getEmail() != null ? request.getEmail().toLowerCase() : null);
        entity.setActive(request.getActive());

        authorRepository.save(entity);
        log.info("Updated author with id {}", entity.getId());
        return new AuthorDTO(entity);
    }

    /**
     * Finds an author by its ID
     *
     * @param id of the author to be found
     * @return the found AuthorDTO
     * @throws NotFoundAlertException if the author does not exist
     */
    @Transactional(readOnly = true)
    public AuthorDTO findAuthorById(Long id){
        Author entity = authorRepository.findByIdAndActiveTrue(id)
               .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_AUTHOR_NOT_FOUND,
                        ErrorMessages.ERROR_AUTOR_NO_ENCONTRADO
                ));

        log.debug("Found author : {}", entity);

        return new AuthorDTO(entity);
    }

    /**
     * Deletes an author by its ID
     *
     * @param id of the author to be deleted
     * @throws NotFoundAlertException if the author does not exist
     */
    public void deleteAuthorById(Long id){
        Author entity = authorRepository.findByIdAndActiveTrue(id)
               .orElseThrow(() -> new NotFoundAlertException(
                        ErrorKeys.ERR_AUTHOR_NOT_FOUND,
                        ErrorMessages.ERROR_AUTOR_NO_ENCONTRADO
                ));

        entity.setActive(false);
        authorRepository.save(entity);
        log.info("Deleted author with id {}", entity.getId());
    }

    /**
     * Finds all authors
     *
     * @return a list of authors
     */
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAllAuthor(){
        return authorRepository.findAllByActiveTrue().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());
    }
}
