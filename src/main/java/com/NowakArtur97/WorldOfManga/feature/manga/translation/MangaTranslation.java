package com.NowakArtur97.WorldOfManga.feature.manga.translation;

import com.NowakArtur97.WorldOfManga.feature.language.Language;
import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "manga_translation", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@EqualsAndHashCode.Exclude
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Language language;
}
