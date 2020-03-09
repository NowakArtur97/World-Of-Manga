package com.NowakArtur97.WorldOfManga.model.neo4j;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NodeEntity(label = "Genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaGenre {

	@Id
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	private String genre;
}
