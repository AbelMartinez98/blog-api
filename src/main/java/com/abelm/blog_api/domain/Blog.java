package com.abelm.blog_api.domain;

import com.abelm.blog_api.domain.enumeration.FrequencyBlogEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotNull
    @Size(max = 50)
    @Column(name = "topic", length = 50, nullable = false)
    private String topic;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private FrequencyBlogEnum frequency;

    @NotNull
    @Column(name = "allow_comments", nullable = false)
    private Boolean allowComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private Author author;

    @Builder.Default
    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
}