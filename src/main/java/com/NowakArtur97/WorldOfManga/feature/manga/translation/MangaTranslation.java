package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import com.NowakArtur97.WorldOfManga.feature.language.Language;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "manga_translation", schema = "world_of_manga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MangaTranslation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manga_translation_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manga_id")
	@ToString.Exclude
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	@ToString.Exclude
	private Language language;

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (!(o instanceof MangaTranslation)) return false;

		MangaTranslation that = (MangaTranslation) o;

		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getTitle(), that.getTitle()) &&
				Objects.equals(getDescription(), that.getDescription());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTitle(), getDescription());
	}
}
