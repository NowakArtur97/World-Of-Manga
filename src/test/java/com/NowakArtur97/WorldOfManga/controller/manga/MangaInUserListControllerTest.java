package com.NowakArtur97.WorldOfManga.controller.manga;

import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.model.User;
import com.NowakArtur97.WorldOfManga.service.MangaInUserListService;
import com.NowakArtur97.WorldOfManga.service.UserService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaInUserListController_Tests")
class MangaInUserListControllerTest {

    private MockMvc mockMvc;

    private MangaInUserListController mangaInUserListController;

    @Mock
    private MangaInUserListService mangaInUserListService;

    @Mock
    private LocaleResolver cookieLocaleResolver;

    @Mock
    private UserService userService;

    @BeforeEach
    private void setUp() {

        mangaInUserListController = new MangaInUserListController(mangaInUserListService, cookieLocaleResolver,
                userService);

        mockMvc = MockMvcBuilders.standaloneSetup(mangaInUserListController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    void when_add_to_list_manga_should_ad_manga_to_list_and_redirect_to_last_page() {

        Long mangaId = 1L;
        int status = 0;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/addToList")
                .param("id", mangaId.toString()).param("status", String.valueOf(status)).header("Referer", "/");

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
                () -> verify(mangaInUserListService, times(1)).addOrRemoveFromList(mangaId, status),
                () -> verifyNoMoreInteractions(mangaInUserListService), () -> verifyNoInteractions(userService));
    }

    @Test
    void when_load_manga_list_by_status_should_show_specific_manga_and_redirect_to_last_page() throws IOException {

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        Set<MangaTranslation> mangaTranslationsExpected = new HashSet<>();
        mangaTranslationsExpected.add(mangaTranslationEnExpected);
        mangaTranslationsExpected.add(mangaTranslationPlExpected);

        Author authorExpected = new Author("FirsName LastName");

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Locale locale = new Locale("en");

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());

        Set<Manga> mangasExpected = new HashSet<>();
        mangasExpected.add(mangaExpected);

        List<Manga> mangasListExpected = new ArrayList<>(mangasExpected);
        Pageable pageable = PageRequest.of(0, 12);
        int start = (int) PageRequest.of(0, 12).getOffset();
        int end = (start + pageable.getPageSize()) > mangasListExpected.size() ? mangasListExpected.size()
                : (start + pageable.getPageSize());
        Page<Manga> mangaPages = new PageImpl<Manga>(mangasListExpected.subList(start, end), pageable,
                mangasListExpected.size());

        int status = 0;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/auth/sortMangaList/{id}", status)
                .locale(locale).flashAttr("locale", locale).header("Referer", "/");

        User userExpected = User.builder().username("username").password("password").email("user@email.com")
                .isEnabled(true).build();

        userExpected.addMangaRating(mangaExpected, 4);
        userExpected.addMangaToFavourites(mangaExpected);

        Set<Manga> usersRatingsExpected = Set.of(mangaExpected);
        List<Manga> usersFavouritesExpected = List.of(mangaExpected);

        when(mangaInUserListService.getUsersMangaListByStatus(status)).thenReturn(mangasExpected);
        when(userService.loadLoggedInUsername()).thenReturn(userExpected);

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(view().name("views/manga-list"))
                        .andExpect(model().attribute("mangas", mangaPages))
                        .andExpect(model().attribute("usersFavourites", usersFavouritesExpected))
                        .andExpect(model().attribute("usersRatings", usersRatingsExpected)),
                () -> verify(mangaInUserListService, times(1)).getUsersMangaListByStatus(status),
                () -> verifyNoMoreInteractions(mangaInUserListService),
                () -> verify(userService, times(1)).loadLoggedInUsername(),
                () -> verifyNoMoreInteractions(userService));
    }
}
