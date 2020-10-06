package com.NowakArtur97.WorldOfManga.service;

import com.NowakArtur97.WorldOfManga.enums.MangaInUserListStatus;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.user.User;
import com.NowakArtur97.WorldOfManga.feature.user.UserService;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaInUserList;
import com.NowakArtur97.WorldOfManga.model.MangaRating;
import com.NowakArtur97.WorldOfManga.repository.MangaInUserListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MangaInUserListService {

    private final MangaInUserListRepository mangaInUserListRepository;

    private final MangaService mangaService;

    private final UserService userService;

    public Optional<MangaInUserList> findByUserAndManga(User user, Manga manga) {

        return mangaInUserListRepository.findByUserAndManga(user, manga);
    }

    @Transactional
    public MangaInUserList addOrRemoveFromList(Long mangaId, int status) throws MangaNotFoundException {

        Manga manga = mangaService.findById(mangaId);

        User user = userService.loadLoggedInUsername();

        MangaInUserListStatus mangaStatus = MangaInUserListStatus.values()[status];

        Optional<MangaInUserList> mangaInListOptional = findByUserAndManga(user, manga);

        MangaInUserList mangaInUserList;

        if (mangaInListOptional.isPresent()) {

            mangaInUserList = mangaInListOptional.get();

            if (mangaStatus.equals(mangaInUserList.getStatus())) {

                user.removeMangaFromList(mangaInUserList);
            } else {

                mangaInUserList.setStatus(mangaStatus);
            }
        } else {

            mangaInUserList = user.addMangaToList(manga, mangaStatus);
        }

        return mangaInUserList;
    }

    public Set<Manga> getUsersMangaListByStatus(int status) {

        Set<Manga> mangaList;

        User user = userService.loadLoggedInUsername();

        if (status >= MangaInUserListStatus.values().length) {

            if (status == 5) {

                mangaList = user.getFavouriteMangas();
            } else {

                mangaList = user.getMangasRatings().stream().map(MangaRating::getManga).collect(Collectors.toSet());
            }
        } else {

            mangaList = getListByStatus(status, user);
        }

        return mangaList;
    }

    private Set<Manga> getListByStatus(int statusId, User user) {

        MangaInUserListStatus status = MangaInUserListStatus.values()[statusId];

        return mapToMangaSet(status, user);
    }

    private Set<Manga> mapToMangaSet(MangaInUserListStatus status, User user) {

        return user.getMangaList().stream().filter(manga -> manga.getStatus().equals(status))
                .collect(Collectors.toSet()).stream().map(MangaInUserList::getManga).collect(Collectors.toSet());
    }
}
