package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
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

    Manga addOrUpdate(MangaDTO mangaDTO, Manga manga) {

        if (mangaDTO.getId() != null) {

            manga.setId(mangaDTO.getId());
            manga.removeAllAuthors();
            manga.removeAllGenres();
        }

        manga = mangaMapper.mapMangaDTOToManga(manga, mangaDTO);

        mangaRepository.save(manga);

        return manga;
    }

    Manga deleteManga(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        manga.deleteAllRelations();

        mangaRepository.delete(manga);

        return manga;
    }

    @Transactional
    Manga addOrRemoveFromFavourites(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        User user = userService.loadLoggedInUsername();

        if (user.getFavouriteMangas().contains(manga)) {

            user.removeMangaFromFavourites(manga);
        } else {

            user.addMangaToFavourites(manga);
        }

        return manga;
    }

    MangaDTO getMangaDTOById(Long mangaId) throws MangaNotFoundException {

        Manga manga = findById(mangaId);

        return mangaMapper.mapMangaToDTO(manga);
    }
}
