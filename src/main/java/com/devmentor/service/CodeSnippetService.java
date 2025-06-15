package com.devmentor.service;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import com.devmentor.repository.CodeSnippetRepository;
import com.devmentor.service.interfaces.ICodeSnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeSnippetService implements ICodeSnippetService {

    private final CodeSnippetRepository repository;

    @Override
    public CodeSnippet submit(CodeSnippet snippet) {
        return repository.save(snippet);
    }

    @Override
    public List<CodeSnippet> getSnippetsByUser(User user) {
        return repository.findBySubmittedBy(user);
    }
}
