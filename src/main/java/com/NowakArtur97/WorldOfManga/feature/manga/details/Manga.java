package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.inUserList.MangaInUserList;
import com.NowakArtur97.WorldOfManga.feature.manga.rating.MangaRating;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

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

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final List<MangaTranslation> translations = new ArrayList<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaRating> mangasRatings = new HashSet<>();

	@OneToMany(mappedBy = "manga", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaInUserList> usersWithMangaInList = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "manga_author", schema = "world_of_manga", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<Author> authors = new HashSet<>();

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "manga_genre", schema = "world_of_manga", joinColumns = @JoinColumn(name = "manga_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaGenre> genres = new HashSet<>();

	@ManyToMany(mappedBy = "favouriteMangas")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<User> userWithMangaInFavourites = new HashSet<>();

	@PostLoad
	public void countRating() {

		this.rating = getMangasRatings().stream().mapToDouble(MangaRating::getRating).average().orElse(0);
	}

	public void addTranslation(MangaTranslation mangaTranslation) {

		this.getTranslations().add(mangaTranslation);
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

	public void addGenre(MangaGenre genreExpected) {

		this.getGenres().add(genreExpected);
		genreExpected.getMangaWithGenre().add(this);
	}

	public void removeAllGenres() {

		for (Iterator<MangaGenre> genreIterator = this.getGenres().iterator(); genreIterator.hasNext();) {
			MangaGenre genre = genreIterator.next();
			genre.removeManga(this);
			genreIterator.remove();
		}
	}

	public void removeAllAuthors() {

		for (Iterator<Author> authorIterator = this.getAuthors().iterator(); authorIterator.hasNext();) {
			Author author = authorIterator.next();
			author.removeManga(this);
			authorIterator.remove();
		}
	}

	public void removeFromAllUsersFavourites() {

		for (Iterator<User> userIterator = this.getUserWithMangaInFavourites().iterator(); userIterator.hasNext();) {
			User user = userIterator.next();
			user.getFavouriteMangas().remove(this);
			userIterator.remove();
		}
	}

	public void removeFromAllUsersLists() {

		for (Iterator<MangaInUserList> userIterator = this.getUsersWithMangaInList().iterator(); userIterator
				.hasNext();) {
			MangaInUserList mangaInList = userIterator.next();
			mangaInList.setManga(null);
			mangaInList.setUser(null);
			userIterator.remove();
		}
	}

	public void removeAllRatings() {

		for (Iterator<MangaRating> ratingIterator = this.getMangasRatings().iterator(); ratingIterator.hasNext();) {
			MangaRating mangaRating = ratingIterator.next();
			mangaRating.setManga(null);
			mangaRating.setUser(null);
			ratingIterator.remove();
		}
	}

	public void deleteAllRelations() {

		this.removeFromAllUsersFavourites();

		this.removeFromAllUsersLists();

		this.removeAllRatings();

		this.removeAllAuthors();

		this.removeAllGenres();
	}
}
