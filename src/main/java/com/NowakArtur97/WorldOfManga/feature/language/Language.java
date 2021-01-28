package com.NowakArtur97.WorldOfManga.feature.language;

import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "language", schema = "world_of_manga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "language_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "locale")
	private String locale;

	@OneToMany(mappedBy = "language", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private final Set<MangaTranslation> mangaTranslations = new HashSet<>();

	public void addTranslation(MangaTranslation mangaTranslation) {

		mangaTranslations.add(mangaTranslation);
		mangaTranslation.setLanguage(this);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (!(o instanceof Language)) return false;

		Language language = (Language) o;

		return Objects.equals(getId(), language.getId()) &&
				Objects.equals(getLocale(), language.getLocale());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getLocale());
	}
}
