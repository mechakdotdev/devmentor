package com.devmentor.controller;

import com.devmentor.dto.request.ReviewCreateRequestDto;
import com.devmentor.dto.response.ReviewResponseDto;
import com.devmentor.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewCreateRequestDto dto) {
        ReviewResponseDto created = reviewService.createReview(dto);

        return ResponseEntity.ok(created);
    }

    @GetMapping("/snippet/{id}")
    public ResponseEntity<ReviewResponseDto> getByCodeSnippetId(@PathVariable UUID id) {
        ReviewResponseDto review = reviewService.getReviewBySnippet(id);

        return ResponseEntity.ok(review);
    }

    @GetMapping("/reviewer")
    public ResponseEntity<List<ReviewResponseDto>> getByReviewer(@RequestParam String username) {
        List<ReviewResponseDto> reviews = reviewService.getReviewsByUsername(username);

        return ResponseEntity.ok(reviews);
    }
}
