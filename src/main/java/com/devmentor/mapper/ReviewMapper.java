package com.devmentor.mapper;

import com.devmentor.dto.request.ReviewCreateRequestDto;
import com.devmentor.dto.response.ReviewResponseDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.Review;
import com.devmentor.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review toEntity(ReviewCreateRequestDto dto, CodeSnippet snippet, List<User> reviewers) {
        return Review.builder()
                .codeSnippet(snippet)
                .reviewers(reviewers)
                .feedback(dto.getFeedback())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .codeSnippetId(review.getCodeSnippet().getId())
                .reviewers(
                        review.getReviewers().stream()
                                .map(User::getUsername)
                                .collect(Collectors.toList())
                )
                .feedback(review.getFeedback())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
