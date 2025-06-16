package com.devmentor.repository;

import com.devmentor.entity.CodeSnippet;
import com.devmentor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, UUID> {
    @Query("SELECT cs FROM CodeSnippet cs JOIN FETCH cs.submittedBy WHERE cs.submittedBy = :user")
    List<CodeSnippet> findBySubmittedBy(@Param("user") User user);
}