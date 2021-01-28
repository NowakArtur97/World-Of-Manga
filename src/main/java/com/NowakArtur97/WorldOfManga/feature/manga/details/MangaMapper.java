package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
class MangaMapper {

    Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO) {

        mapAuthors(manga, mangaDTO.getAuthors());

        mapGenres(manga, mangaDTO.getGenres());

        mapMultipartFileToBlob(manga, mangaDTO);

        return manga;
    }

    MangaDTO mapMangaToDTO(Manga manga) {

        MangaTranslation enTranslation = manga.getTranslations().get(Manga.EN_TRANSLATION_INDEX);
        MangaTranslation plTranslation = manga.getTranslations().get(Manga.PL_TRANSLATION_INDEX);

        MangaTranslationDTO enTranslationDTO = MangaTranslationDTO.builder().title(enTranslation.getTitle())
                .description(enTranslation.getDescription()).build();

        MangaTranslationDTO plTranslationDTO = MangaTranslationDTO.builder().title(plTranslation.getTitle())
                .description(plTranslation.getDescription()).build();

        return MangaDTO.builder().id(manga.getId()).enTranslation(enTranslationDTO)
                .plTranslation(plTranslationDTO).authors(manga.getAuthors()).genres(manga.getGenres()).build();
    }

    private void mapAuthors(Manga manga, Set<Author> authors) {

        authors.forEach(manga::addAuthor);
    }

    private void mapGenres(Manga manga, Set<MangaGenre> genres) {

        genres.forEach(manga::addGenre);
    }

    private void mapMultipartFileToBlob(Manga manga, MangaDTO mangaDTO) {

        try {
            manga.setImage(mangaDTO.getImage().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
