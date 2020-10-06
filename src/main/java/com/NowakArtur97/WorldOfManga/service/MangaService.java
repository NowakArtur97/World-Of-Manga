package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.mapper.manga.MangaMapper;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.repository.MangaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MangaService {

    private final MangaRepository mangaRepository;

    private final MangaMapper mangaMapper;

    private final UserService userService;

    public Manga addOrUpdate(MangaDTO mangaDTO, Manga manga) throws MangaNotFoundException {

        if (mangaDTO.getId() != null) {

            manga.setId(mangaDTO.getId());
            manga.removeAllAuthors();
            manga.removeAllGenres();
        }

        manga = mangaMapper.mapMangaDTOToManga(manga, mangaDTO);

        mangaRepository.save(manga);

        return manga;
    }

    public Manga deleteManga(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        manga.deleteAllRelations();

        mangaRepository.delete(manga);

        return manga;
    }

    @Transactional
    public Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        User user = userService.loadLoggedInUsername();

        if (user.getFavouriteMangas().contains(manga)) {

            user.removeMangaFromFavourites(manga);
        } else {

            user.addMangaToFavourites(manga);
        }

        return manga;
    }

    public MangaDTO getMangaDTOById(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        return mangaMapper.mapMangaToDTO(manga);
    }

    public Manga findById(Long id) throws MangaNotFoundException {

        return mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException("Manga with id:" + id + " not found"));
    }

    public List<Manga> findAll() {

        return mangaRepository.findAll();
    }

    public Page<Manga> findAllDividedIntoPages(Pageable pageable) {

        return mangaRepository.findAll(pageable);
    }
}
