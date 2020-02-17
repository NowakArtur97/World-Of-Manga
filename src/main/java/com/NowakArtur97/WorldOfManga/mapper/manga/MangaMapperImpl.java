package com.NowakArtur97.WorldOfManga.mapper.manga;

import java.io.IOException;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@Service
public class MangaMapperImpl implements MangaMapper {

	@Override
	public Manga mapMangaDTOToManga(MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations) {

		Manga manga = new Manga();

		mapMangaTranslations(manga, mangaTranslations);

		mapAuthors(manga, mangaDTO.getAuthors());

		mapMultipartFileToBlob(manga, mangaDTO);

		return manga;
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
