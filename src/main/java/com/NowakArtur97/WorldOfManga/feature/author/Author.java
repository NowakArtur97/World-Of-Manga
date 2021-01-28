package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.feature.manga.details.Manga;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author", schema = "world_of_manga")
@Getter
@Setter
@NoArgsConstructor
@ToString
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
	private final Set<Manga> createdMangas = new HashSet<>();

	public Author(String fullName) {
		this.fullName = fullName;
	}

	public void removeManga(Manga manga) {

		this.getCreatedMangas().remove(manga);
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (!(o instanceof Author)) return false;

		Author author = (Author) o;

		return Objects.equals(getId(), author.getId()) &&
				Objects.equals(getFullName(), author.getFullName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFullName());
	}
}
