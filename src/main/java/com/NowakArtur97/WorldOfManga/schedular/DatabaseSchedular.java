package com.NowakArtur97.WorldOfManga.schedular;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Language;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.service.api.LanguageService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DatabaseSchedular {

	private final AuthorService authorService;

	private final LanguageService languageService;

	@EventListener(ApplicationReadyEvent.class)
	public void onStartup() throws LanguageNotFoundException {
		saveMangas();
	}

	private void saveMangas() throws LanguageNotFoundException {

		Language en = languageService.findByLocale("en");
		Language pl = languageService.findByLocale("pl");

		String tokyoGhouldTitle = "Tokyo Ghould";
		String tokyoGhouldDescriptionEn = "Ghouls live among us, the same as normal people in every way—except their craving for human flesh.\r\n"
				+ "Ken Kaneki is an ordinary college student until a violent encounter turns him into the first half-human half-ghoul hybrid. Trapped between two worlds, he must survive Ghoul turf wars, learn more about Ghoul society and master his new powers. ";
		String tokyoGhoulDescriptionPl = "To tu skrywa się źródło \"rozpaczy\". Wtapiają się w tłum, polują na ludzi i pożerają zwłoki. Nazywa się je \"ghulami\". Losy młodzieńca, który przez przypadek styka się z taką istotą, zmieniają się całkowicie. Jego życie już nigdy nie będzie takie jak dawniej...";

		MangaTranslation tokyoGhoulTranslationEn = MangaTranslation.builder().title(tokyoGhouldTitle)
				.description(tokyoGhouldDescriptionEn).language(en).build();
		MangaTranslation tokyoGhoulTranslationPl = MangaTranslation.builder().title(tokyoGhouldTitle)
				.description(tokyoGhoulDescriptionPl).language(pl).build();

		byte[] tokyoGhoulImage = null;
		try {
			tokyoGhoulImage = convertImageToByte(
					new URL("https://f01.mrcdn.info/file/mrportal/i/4/d/k/sC.cPf-4DqC.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Manga tokyoGhoulManga = Manga.builder().image(tokyoGhoulImage).build();
		tokyoGhoulManga.addTranslation(tokyoGhoulTranslationEn);
		tokyoGhoulManga.addTranslation(tokyoGhoulTranslationPl);

		Author tokyoGhoulAuthor = new Author("Sui Ishida");
		tokyoGhoulAuthor.addManga(tokyoGhoulManga);
		authorService.save(tokyoGhoulAuthor);
	}

	public static byte[] convertImageToByte(URL url) throws IOException {

		InputStream inputStream = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		byte[] buf = new byte[1024];
		int n = 0;
		
		while (-1 != (n = inputStream.read(buf))) {
			byteArrayOutputStream.write(buf, 0, n);
		}
		
		byteArrayOutputStream.close();
		inputStream.close();
		
		return byteArrayOutputStream.toByteArray();
	}
}
