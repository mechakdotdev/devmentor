package com.devmentor.service;

import com.devmentor.dto.request.ReviewCreateRequestDto;
import com.devmentor.dto.response.ReviewResponseDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.Review;
import com.devmentor.entity.User;
import com.devmentor.exception.ResourceNotFoundException;
import com.devmentor.mapper.ReviewMapper;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CodeSnippetRepository snippetRepository;
    private final UserService userService;

    public ReviewResponseDto createReview(ReviewCreateRequestDto dto) {
        CodeSnippet snippet = snippetRepository.findById(dto.getCodeSnippetId())
                .orElseThrow(() -> new ResourceNotFoundException("Code snippet not found"));

        List<User> reviewers = userService.getUsers(dto.getReviewers());

        if (reviewers.size() != dto.getReviewers().size()) {
            throw new ResourceNotFoundException("One or more reviewers were not found");
        }

        Review review = ReviewMapper.toEntity(dto, snippet, reviewers);
        Review saved = reviewRepository.save(review);
        return ReviewMapper.toDto(saved);
    }

    public ReviewResponseDto getReviewBySnippet(UUID snippetId) {
        Review review = reviewRepository.findByCodeSnippetId(snippetId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found" + snippetId));

        return ReviewMapper.toDto(review);
    }

    public List<ReviewResponseDto> getReviewsByUsername(String username) {
        User reviewer = userService.getUser(username);

        List<Review> reviews = reviewRepository.findByReviewersContaining(reviewer)
                .orElseThrow(() -> new ResourceNotFoundException("No reviews found for user" + username));

        return reviews.stream()
                .map(ReviewMapper::toDto)
                .toList();
    }
}