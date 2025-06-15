package com.devmentor.controller;

import com.devmentor.dto.CodeSnippetRequestDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.mapper.CodeSnippetMapper;
import com.devmentor.service.CodeSnippetService;
import com.devmentor.service.UserService;
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
    public ResponseEntity<CodeSnippet> submitSnippet(@RequestBody CodeSnippetRequestDto dto) {
        User user = userService.getByUsername(dto.getUsername());
        CodeSnippet saved = snippetService.submit(CodeSnippetMapper.toEntity(dto, user));

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<CodeSnippet>> getSnippets(@PathVariable String username) {
        User user = userService.getByUsername(username);

        return ResponseEntity.ok(snippetService.getSnippetsByUser(user));
    }
}