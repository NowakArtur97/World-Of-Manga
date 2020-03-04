package com.NowakArtur97.WorldOfManga.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "author", schema = "world_of_manga")
@Data
@NoArgsConstructor
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "full_name")
	private String fullName;

	@ManyToMany(mappedBy = "authors")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private final Set<Manga> createdMangas = new HashSet<>();

	public Author(String fullName) {
		this.fullName = fullName;
	}

	public void removeManga(Manga manga) {

		this.getCreatedMangas().remove(manga);
	}
}
