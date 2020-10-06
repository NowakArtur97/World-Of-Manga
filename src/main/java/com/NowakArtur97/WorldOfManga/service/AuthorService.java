package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorMapper;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;


    public Author addOrUpdate(AuthorDTO authorDTO) {

        Author author = authorMapper.mapAuthorDTOToAuthor(authorDTO);

        return save(author);
    }

    public boolean isAuthorAlreadyInDatabase(String fullName) {

        return authorRepository.existsAuthorByFullName(fullName);
    }

    public List<Author> findAll() {

        return authorRepository.findAll();
    }

    public Author save(Author author) {

        return authorRepository.save(author);
    }
}
