package com.NowakArtur97.WorldOfManga.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NowakArtur97.WorldOfManga.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	boolean existsAuthorByFullName(String fullName);
}
