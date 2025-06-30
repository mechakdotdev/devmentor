package com.devmentor.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "code_snippet")
public class CodeSnippet {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String language;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime submittedAt;

    @OneToOne(mappedBy = "codeSnippet", cascade = CascadeType.ALL)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User submittedBy;
}