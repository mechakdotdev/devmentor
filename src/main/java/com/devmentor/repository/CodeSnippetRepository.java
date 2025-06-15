package com.devmentor.repository;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, UUID> {
    List<CodeSnippet> findBySubmittedBy(User user);
}