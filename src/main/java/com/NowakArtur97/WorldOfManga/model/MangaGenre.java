package com.NowakArtur97.WorldOfManga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

	@Column(name = "genre")
	private String genre;

	public MangaGenre(String genre) {
		this.genre = genre;
	}

	@ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<Manga> mangaWithGenre = new HashSet<>();

	public void removeManga(Manga manga) {

		this.getMangaWithGenre().remove(manga);
	}
}
