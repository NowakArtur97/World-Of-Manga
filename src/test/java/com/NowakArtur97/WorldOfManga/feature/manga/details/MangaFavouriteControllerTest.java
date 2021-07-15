package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("Manga_Tests")
@Tag("Unit_Tests")
@Tag("MangaFavouriteController_Tests")
class MangaFavouriteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MangaService mangaService;

    @BeforeEach
    void setUp() {

        MangaFavouriteController mangaFavouriteController = new MangaFavouriteController(mangaService);
        mockMvc = MockMvcBuilders.standaloneSetup(mangaFavouriteController).build();
    }

    @Test
    void when_add_or_remove_manga_from_favourites_should_redirect_to_last_page() {

        Long mangaId = 1L;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/auth/addOrRemoveFromFavourites/{id}", mangaId).header("Referer", "/");

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
                () -> verify(mangaService, times(1)).addOrRemoveFromFavourites(mangaId));
    }
}
