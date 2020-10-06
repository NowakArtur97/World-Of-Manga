package com.NowakArtur97.WorldOfManga.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genre", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	@EqualsAndHashCode.Exclude
	private final Set<Manga> mangaWithGenre = new HashSet<>();

	public void removeManga(Manga manga) {

		this.getMangaWithGenre().remove(manga);
	}
}
