package com.NowakArtur97.WorldOfManga.mapper.manga;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaGenre;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@Service
public class MangaMapperImpl implements MangaMapper {

	@Override
	public Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO) {

		mapAuthors(manga, mangaDTO.getAuthors());

		mapGenres(manga, mangaDTO.getGenres());

		mapMultipartFileToBlob(manga, mangaDTO);

		return manga;
	}

	@Override
	public MangaDTO mapMangaToDTO(Manga manga) {

		MangaTranslation enTranslation = manga.getTranslations().get(Manga.EN_TRANSLATION_INDEX);
		MangaTranslation plTranslation = manga.getTranslations().get(Manga.PL_TRANSLATION_INDEX);

		MangaTranslationDTO enTranslationDTO = MangaTranslationDTO.builder().title(enTranslation.getTitle())
				.description(enTranslation.getDescription()).build();

		MangaTranslationDTO plTranslationDTO = MangaTranslationDTO.builder().title(plTranslation.getTitle())
				.description(plTranslation.getDescription()).build();

		MangaDTO mangaDTO = MangaDTO.builder().id(manga.getId()).enTranslation(enTranslationDTO)
				.plTranslation(plTranslationDTO).authors(manga.getAuthors()).genres(manga.getGenres()).build();

		return mangaDTO;
	}

	private void mapAuthors(Manga manga, Set<Author> authors) {

		authors.stream().forEach(author -> manga.addAuthor(author));
	}

	private void mapGenres(Manga manga, Set<MangaGenre> genres) {

		genres.stream().forEach(genre -> manga.addGenre(genre));
	}

	private void mapMultipartFileToBlob(Manga manga, MangaDTO mangaDTO) {

		try {
			manga.setImage(mangaDTO.getImage().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
