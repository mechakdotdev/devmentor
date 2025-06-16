package com.devmentor.service.interfaces;

import com.devmentor.dto.response.CodeSnippetResponseDto;
import com.devmentor.entity.CodeSnippet;

import java.util.List;

public interface ICodeSnippetService {
    CodeSnippet submit(CodeSnippet snippet);
    List<CodeSnippetResponseDto> getSnippetsByUsername(String username);
}
