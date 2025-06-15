package com.devmentor.service.interfaces;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;

import java.util.List;

public interface ICodeSnippetService {
    CodeSnippet submit(CodeSnippet snippet);
    List<CodeSnippet> getSnippetsByUser(User user);
}
