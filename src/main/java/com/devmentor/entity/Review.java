package com.devmentor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "review")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    private CodeSnippet codeSnippet;

    @ManyToMany
    @JoinTable(
            name = "reviewer",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    @Builder.Default
    private List<User> reviewers = new ArrayList<>();

    private String feedback;

    private LocalDateTime createdAt;
}