package com.NowakArtur97.WorldOfManga.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MangaInUserListId implements Serializable {

	private static final long serialVersionUID = -1934008381091284254L;

	@Column(name = "manga_id")
	private Long mangaId;

	@Column(name = "user_id")
	private Long userId;
}
