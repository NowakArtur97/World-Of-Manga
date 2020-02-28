package com.NowakArtur97.WorldOfManga.mapper.manga;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;

@Service
public class MangaMapperImpl implements MangaMapper {

	@Override
	public Manga mapMangaDTOToManga(Manga manga, MangaDTO mangaDTO, Set<MangaTranslation> mangaTranslations) {

		if (mangaDTO.getId() != null) {
			manga.setId(mangaDTO.getId());
			clearMangaPrevoiusData(manga);
		}

		mapMangaTranslations(manga, mangaTranslations);

		mapAuthors(manga, mangaDTO.getAuthors());

		mapMultipartFileToBlob(manga, mangaDTO);

		return manga;
	}

	@Override
	public MangaDTO mapMangaToDTO(Manga manga) {

		MangaTranslationDTO enTranslation = MangaTranslationDTO.builder()
				.title(manga.getTranslations().get(0).getTitle())
				.description(manga.getTranslations().get(0).getDescription()).build();

		MangaTranslationDTO plTranslation = MangaTranslationDTO.builder()
				.title(manga.getTranslations().get(1).getTitle())
				.description(manga.getTranslations().get(1).getDescription()).build();

		MangaDTO mangaDTO = MangaDTO.builder().id(manga.getId()).enTranslation(enTranslation)
				.plTranslation(plTranslation).authors(manga.getAuthors()).build();

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

	private void clearMangaPrevoiusData(Manga manga) {

		for (Iterator<MangaTranslation> translationIterator = manga.getTranslations().iterator(); translationIterator
				.hasNext();) {
			MangaTranslation translation = translationIterator.next();
			translation.setManga(null);
			translationIterator.remove();
		}

		for (Iterator<Author> authorIterator = manga.getAuthors().iterator(); authorIterator.hasNext();) {
			Author author = authorIterator.next();
			author.removeManga(manga);
			authorIterator.remove();
		}
	}
}
