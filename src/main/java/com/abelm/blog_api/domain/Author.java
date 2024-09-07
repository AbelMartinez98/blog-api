package com.abelm.blog_api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "paternal_surname", length = 100)
    private String paternalSurname;

    @Size(max = 100)
    @Column(name = "maternal_surname", length = 100)
    private String maternalSurname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 50)
    @Column(name = "residence_country", length = 50)
    private String residenceCountry;

    @Column(name = "email", unique = true, nullable = false)
    @Email
    private String email;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
}
