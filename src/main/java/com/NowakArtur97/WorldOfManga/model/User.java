package com.NowakArtur97.WorldOfManga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
@Table(name = "user", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "user_role", schema = "world_of_manga", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private final Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<MangaRating> mangasRatings = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "favourite_manga", schema = "world_of_manga", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "manga_id"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<Manga> favouriteMangas = new HashSet<>();

	public void addRole(Role role) {

		this.getRoles().add(role);
	}

	public MangaRating addMangaRating(Manga manga, int rating) {

		MangaRating mangaRating = new MangaRating(manga, this, rating);

		this.getMangasRatings().add(mangaRating);
		manga.getMangasRatings().add(mangaRating);

		return mangaRating;
	}

	public Manga addMangaToFavourites(Manga manga) {

		this.getFavouriteMangas().add(manga);
		manga.getUserWithMangaInFavourites().add(this);

		return manga;
	}

	public Manga removeMangaFromFavourites(Manga manga) {

		this.getFavouriteMangas().remove(manga);
		manga.getUserWithMangaInFavourites().remove(this);

		return manga;
	}
}
