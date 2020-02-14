package com.NowakArtur97.WorldOfManga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	public Author(String fullName) {
		this.fullName = fullName;
	}
}
