package com.abelm.blog_api.service.dto;

import com.abelm.blog_api.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;

    private String firstName;

    private String paternalSurname;

    private String maternalSurname;

    private LocalDate birthDate;

    private String residenceCountry;

    private String email;

    private Boolean active;

    public AuthorDTO(Author author){
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.paternalSurname = author.getPaternalSurname();
        this.maternalSurname = author.getMaternalSurname();
        this.birthDate = author.getBirthDate();
        this.residenceCountry = author.getResidenceCountry();
        this.email = author.getEmail();
        this.active = author.getActive();
    }
}
