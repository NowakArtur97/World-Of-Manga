package com.NowakArtur97.WorldOfManga.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "manga", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manga {

	public final static Integer EN_TRANSLATION_INDEX = 0;
	public final static Integer PL_TRANSLATION_INDEX = 1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manga_id")
	private Long id;

	@Column(name = "image")
	@Type(type = "org.hibernate.type.BinaryType")
	@Lob
	private byte[] image;

	@Transient
	private Double rating;

	@ManyToMany(mappedBy = "createdMangas")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<Author> authors = new HashSet<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final List<MangaTranslation> translations = new ArrayList<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaRating> mangasRatings = new HashSet<>();

	@ManyToMany(mappedBy = "favouriteMangas")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<User> userWithMangaInFavourites = new HashSet<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaInUserList> usersWithMangaInList = new HashSet<>();

	@PostLoad
	public void countRating() {

		this.rating = getMangasRatings().stream().mapToDouble(MangaRating::getRating).average().orElse(0);
	}

	public void addTranslation(MangaTranslation mangaTranslation) {

		this.translations.add(mangaTranslation);
		mangaTranslation.setManga(this);
	}

	public void removeTranslation(MangaTranslation mangaTranslation) {

		this.getTranslations().remove(mangaTranslation);
		mangaTranslation.setManga(null);
	}

	public void addAuthor(Author author) {

		this.getAuthors().add(author);
		author.getCreatedMangas().add(this);
	}

	public void removeAuthor(Author author) {

		this.getAuthors().remove(author);
		author.getCreatedMangas().remove(this);
	}

	public void removeAllAuthors() {

		for (Iterator<Author> authorIterator = this.getAuthors().iterator(); authorIterator.hasNext();) {
			Author author = authorIterator.next();
			author.removeManga(this);
			authorIterator.remove();
		}
	}
}
