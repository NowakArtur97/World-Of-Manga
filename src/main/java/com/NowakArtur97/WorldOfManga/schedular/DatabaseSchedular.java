package com.NowakArtur97.WorldOfManga.schedular;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.NowakArtur97.WorldOfManga.converter.ImageToByteConverter;
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

	private final ImageToByteConverter imageToByteConverterImpl;

	private final AuthorService authorService;

	private final LanguageService languageService;

	private static Language en;
	private static Language pl;

	@EventListener(ApplicationReadyEvent.class)
	public void onStartup() throws LanguageNotFoundException, MalformedURLException {

		en = languageService.findByLocale("en");
		pl = languageService.findByLocale("pl");

		String tokyoGhoulTitle = "Tokyo Ghoul";
		String tokyoGhoulDescriptionEn = "Ghouls live among us, the same as normal people in every way—except their craving for human flesh.\r\n"
				+ "Ken Kaneki is an ordinary college student until a violent encounter turns him into the first half-human half-ghoul hybrid. Trapped between two worlds, he must survive Ghoul turf wars, learn more about Ghoul society and master his new powers. ";
		String tokyoGhoulDescriptionPl = "To tu skrywa się źródło \"rozpaczy\". Wtapiają się w tłum, polują na ludzi i pożerają zwłoki. Nazywa się je \"ghulami\". Losy młodzieńca, który przez przypadek styka się z taką istotą, zmieniają się całkowicie. Jego życie już nigdy nie będzie takie jak dawniej...";
		URL tokyoGhoulURL = new URL("https://f01.mrcdn.info/file/mrportal/i/4/d/k/sC.cPf-4DqC.png");
		String tokyoGhoulAuthor = "Sui Ishida";

		String soloLevelingTitle = "Solo Leveling";
		String soloLevelingDescriptionEn = "10 years ago, after “the Gate” that connected the real world with the monster world opened, some of the ordinary, everyday people received the power to hunt monsters within the Gate. They are known as \"Hunters\". However, not all Hunters are powerful. My name is Sung Jin-Woo, an E-rank Hunter. I'm someone who has to risk his life in the lowliest of dungeons, the \"World's Weakest\". Having no skills whatsoever to display, I barely earned the required money by fighting in low-leveled dungeons… at least until I found a hidden dungeon with the hardest difficulty within the D-rank dungeons! In the end, as I was accepting death, I suddenly received a strange power, a quest log that only I could see, a secret to leveling up that only I know about! If I trained in accordance with my quests and hunted monsters, my level would rise. Changing from the weakest Hunter to the strongest S-rank Hunter!";
		String soloLevelingescriptionPl = "10 lat temu, po \"Bramie\", która połączyła prawdziwy świat ze światem potworów, niektórzy zwykli ludzie otrzymali moc polowania na potwory w Bramie. Tacy ludzie są znani jako \"Łowcy\". Jednak nie wszyscy Łowcy są potężni. Nazywam się Sung Jin-Woo, łowca E-rank. Jestem kimś, kto musi zaryzykować życie w najniżej położonych lochach, \"Najsłabszych na świecie\". Nie mając żadnych umiejętności do pokazania, ledwo zarobiłem wymagane pieniądze, walcząc w lochach o niskim poziomie... przynajmniej dopóki nie znalazłem ukrytego lochu z najtrudniejszą przeszkodą w lochu D-rank! W końcu, gdy akceptowałem śmierć, nagle otrzymałem dziwną moc, dziennik misji, który tylko ja mogłem zobaczyć, sekret do wyexpienia, o którym tylko ja wiem! Gdybym trenował zgodnie z moimi zadaniami i ściganymi potworami, mój poziom powiększyłby się. Zmieniłbym się z najsłabszego Łowcy E-rank, do Najsilniejszego S-rank!";
		URL soloLevelingURL = new URL("https://img.mrcdn.info/file/mrportal/j/7/9/3/QT.batr6sf-.jpg");
		String soloLevelingAuthor = "Gee So-Lyung";

		String beastarsTitle = "Beastars";
		String beastarsDescriptionEn = "In a world populated by anthropomorphic animals, herbivores and carnivores coexist. For the adolescences of Cherryton Academy, school life is filled with hope, romance, distrust, and uneasiness.The main character is Legoshi the wolf, a member of the drama club. Despite his menacing appearance, he has a very gentle heart. Throughout most of his life, he has always been an object to be feared and hated by other animals, and he has become quite accustomed to that lifestyle. But following the murder of a classmate, he finds himself becoming more involved with his other peers, who have their own share of insecurities, and finds his life in school changing slowly.";
		String beastarsescriptionPl = "W świecie zamieszkałym przez antropomorficzne zwierzęta, gdzie roślinożercy i mięsożercy współpracują ze sobą, rozwija się Akademia Cherryton, szkoła wypełniona nadzieją, romansem, nieufnością i niepokojem. Główny bohater Legoshi jest wilkiem, a zarazem członkiem klubu teatralnego. Pomimo jego groźnego wyglądu, ma bardzo delikatne serce. Przez większość jego życia, inne zwierzęta bały się go, a niektóre nienawidziły, był całkiem przyzwyczajony do tego stylu życia, lecz nagle, zauważył, że staje się bardziej zaangażowany w relacje ze swoimi znajomymi z klasy, którzy dzielą się między sobą swoją niepewnością i złym samopoczuciem, wynikającym z bycia w pozycji w której są. Szkolne życie Legoshiego powoli zaczyna się zmieniać...";
		URL beastarsURL = new URL("https://img.mrcdn.info/file/mrportal/h/8/8/g/Qw.veTHmoI.jpg");
		String beastarsAuthor = "Paru Itagaki";

		saveManga(tokyoGhoulTitle, tokyoGhoulDescriptionEn, tokyoGhoulTitle, tokyoGhoulDescriptionPl, tokyoGhoulURL,
				tokyoGhoulAuthor);
		saveManga(soloLevelingTitle, soloLevelingDescriptionEn, soloLevelingTitle, soloLevelingescriptionPl,
				soloLevelingURL, soloLevelingAuthor);
		saveManga(beastarsTitle, beastarsDescriptionEn, beastarsTitle, beastarsescriptionPl, beastarsURL,
				beastarsAuthor);
	}

	private void saveManga(String titleEn, String descriptionEn, String titlePl, String descriptionPl, URL imageURL,
			String authorFullName) throws LanguageNotFoundException {

		MangaTranslation mangaTranslationEn = MangaTranslation.builder().title(titleEn).description(descriptionEn)
				.language(en).build();
		MangaTranslation mangaTranslationPl = MangaTranslation.builder().title(titlePl).description(descriptionPl)
				.language(pl).build();

		byte[] image = null;

		try {
			image = imageToByteConverterImpl.convertImageToByte(imageURL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Manga manga = Manga.builder().image(image).build();
		manga.addTranslation(mangaTranslationEn);
		manga.addTranslation(mangaTranslationPl);

		Author author = new Author(authorFullName);
		author.addManga(manga);
		authorService.save(author);
	}
}
