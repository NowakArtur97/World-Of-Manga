package com.NowakArtur97.WorldOfManga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manga", schema = "world_fo_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manga {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manga_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
}
