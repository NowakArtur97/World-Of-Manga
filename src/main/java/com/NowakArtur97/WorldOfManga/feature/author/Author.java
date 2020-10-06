package com.NowakArtur97.WorldOfManga.feature.author;

import com.NowakArtur97.WorldOfManga.model.Manga;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
