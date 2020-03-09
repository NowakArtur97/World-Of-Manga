package com.NowakArtur97.WorldOfManga.model.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NodeEntity(label = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	private String username;

	public User(String username) {
		this.username = username;
	}

	@Relationship(type = "HAS_LIKED")
	private final Set<Manga> favouriteManga = new HashSet<>();
}
