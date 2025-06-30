package com.devmentor.repository;

import com.devmentor.entity.Review;
import com.devmentor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findByCodeSnippetId(UUID codeSnippetId);
    Optional<List<Review>> findByReviewersContaining(User reviewer);
}