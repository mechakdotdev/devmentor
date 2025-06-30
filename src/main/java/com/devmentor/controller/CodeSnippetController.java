package com.devmentor.controller;

import com.devmentor.dto.request.CodeSnippetRequestDto;
import com.devmentor.dto.response.CodeSnippetResponseDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.mapper.CodeSnippetMapper;
import com.devmentor.service.CodeSnippetService;
import com.devmentor.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/snippets")
@RequiredArgsConstructor
public class CodeSnippetController {

    private final CodeSnippetService snippetService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CodeSnippet> submitSnippet(@Valid @RequestBody CodeSnippetRequestDto dto) {
        User user = userService.getUser(dto.getUsername());
        CodeSnippet saved = snippetService.submit(CodeSnippetMapper.toEntity(dto, user));

        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<CodeSnippetResponseDto>> getSnippetsByUsername(@RequestParam String username) {
        List<CodeSnippetResponseDto> snippets = snippetService.getSnippetsByUsername(username);

        return ResponseEntity.ok(snippets);
    }
}