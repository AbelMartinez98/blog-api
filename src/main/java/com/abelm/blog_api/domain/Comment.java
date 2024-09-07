package com.abelm.blog_api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString(exclude = {"blog"})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "commenter_name", length = 50, nullable = false)
    private String commenterName;

    @NotNull
    @Email
    @Column(name = "commenter_email", nullable = false)
    private String commenterEmail;

    @Size(max = 50)
    @Column(name = "commenter_country", length = 50)
    private String commenterCountry;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Min(0)
    @Max(10)
    @Column(name = "rating", nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;
}
