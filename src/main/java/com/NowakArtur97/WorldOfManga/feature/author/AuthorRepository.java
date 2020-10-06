package com.NowakArtur97.WorldOfManga.feature.author;

import org.springframework.data.jpa.repository.JpaRepository;

interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsAuthorByFullName(String fullName);
}
