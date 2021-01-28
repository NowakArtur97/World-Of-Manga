package com.NowakArtur97.WorldOfManga.feature.manga.genre;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genre", schema = "world_of_manga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MangaGenre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "genre_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "en_translation")
	private String englishTranslation;

	@Column(name = "pl_translation")
	private String polishTranslation;

	public MangaGenre(String englishTranslation, String polishTranslation) {

		this.englishTranslation = englishTranslation;
		this.polishTranslation = polishTranslation;
	}

	@ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
	@ToString.Exclude
	private final Set<Manga> mangaWithGenre = new HashSet<>();

	public void removeManga(Manga manga) {

		this.getMangaWithGenre().remove(manga);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (!(o instanceof MangaGenre)) return false;

		MangaGenre that = (MangaGenre) o;

		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getEnglishTranslation(), that.getEnglishTranslation()) &&
				Objects.equals(getPolishTranslation(), that.getPolishTranslation());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getEnglishTranslation(), getPolishTranslation());
	}
}
