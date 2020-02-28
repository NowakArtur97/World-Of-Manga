package com.NowakArtur97.WorldOfManga.mapper.manga;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@Service
public class MangaMapperImpl implements MangaMapper {

	private final static Integer EN_TRANSLATION_INDEX = 0;
	private final static Integer PL_TRANSLATION_INDEX = 1;

	@Override
	public Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations) {

		mapMangaTranslations(manga, mangaTranslations);

		mapAuthors(manga, mangaDTO.getAuthors());

		mapMultipartFileToBlob(manga, mangaDTO);

		return manga;
	}

	@Override
	public MangaDTO mapMangaToDTO(Manga manga) {

		MangaTranslation enTranslation = manga.getTranslations().get(EN_TRANSLATION_INDEX);
		MangaTranslation plTranslation = manga.getTranslations().get(PL_TRANSLATION_INDEX);

		MangaTranslationDTO enTranslationDTO = MangaTranslationDTO.builder().title(enTranslation.getTitle())
				.description(enTranslation.getDescription()).build();

		MangaTranslationDTO plTranslationDTO = MangaTranslationDTO.builder().title(plTranslation.getTitle())
				.description(plTranslation.getDescription()).build();

		MangaDTO mangaDTO = MangaDTO.builder().id(manga.getId()).enTranslation(enTranslationDTO)
				.plTranslation(plTranslationDTO).authors(manga.getAuthors()).build();

		return mangaDTO;
	}

	private void mapMangaTranslations(Manga manga, Set<MangaTranslation> mangaTranslations) {

		mangaTranslations.stream().forEach(translation -> manga.addTranslation(translation));
	}

	private void mapAuthors(Manga manga, Set<Author> authors) {

		authors.stream().forEach(author -> manga.addAuthor(author));
	}

	private void mapMultipartFileToBlob(Manga manga, MangaDTO mangaDTO) {

		try {
			manga.setImage(mangaDTO.getImage().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
