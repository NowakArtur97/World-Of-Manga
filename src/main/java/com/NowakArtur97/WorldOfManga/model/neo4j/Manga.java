package com.NowakArtur97.WorldOfManga.model.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NodeEntity(label = "Manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manga {

	@Id
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Relationship(type = "HAS")
	private final Set<MangaGenre> genres = new HashSet<>();
}
