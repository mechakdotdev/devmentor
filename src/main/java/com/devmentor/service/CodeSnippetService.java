package com.devmentor.service;

import com.devmentor.dto.response.CodeSnippetResponseDto;
import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.mapper.CodeSnippetMapper;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.repository.UserRepository;
import com.devmentor.service.interfaces.ICodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeSnippetService implements ICodeSnippetService {

    private final UserRepository userRepository;
    private final CodeSnippetRepository codeSnippetRepository;

    @Override
    public CodeSnippet submit(CodeSnippet snippet) {
        return codeSnippetRepository.save(snippet);
    }

    @Override
    public List<CodeSnippetResponseDto> getSnippetsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CodeSnippet> snippets = codeSnippetRepository.findBySubmittedBy(user);

        return snippets.stream()
                .map(CodeSnippetMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
