package com.NowakArtur97.WorldOfManga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "manga_translation", schema = "world_of_manga")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaTranslation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "manga_translation_id")
	@Setter(value = AccessLevel.PRIVATE)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manga_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Manga manga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Language language;
}
