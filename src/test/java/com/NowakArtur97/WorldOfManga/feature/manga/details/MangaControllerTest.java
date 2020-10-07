package com.NowakArtur97.WorldOfManga.feature.manga.details;

import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.feature.author.Author;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorDTO;
import com.NowakArtur97.WorldOfManga.feature.author.AuthorService;
import com.NowakArtur97.WorldOfManga.feature.manga.details.*;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenre;
import com.NowakArtur97.WorldOfManga.feature.manga.genre.MangaGenreService;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslation;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.feature.manga.translation.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.testUtil.generator.NameWithSpacesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(NameWithSpacesGenerator.class)
@Tag("MangaController_Tests")
class MangaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MangaService mangaService;

    @Mock
    private MangaTranslationService mangaTranslationService;

    @Mock
    private MangaValidator mangaValidator;

    @Mock
    private AuthorService authorService;

    @Mock
    private MangaGenreService mangaGenreService;

    @Mock
    private LocaleResolver cookieLocaleResolver;

    @BeforeEach
    void setUp() {

        MangaController mangaController = new MangaController(mangaService, mangaTranslationService, mangaGenreService, mangaValidator,
                authorService, cookieLocaleResolver);
        mockMvc = MockMvcBuilders.standaloneSetup(mangaController).build();
    }

    @Test
    void when_load_add_manga_page_should_show_manga_form() {

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("FirstName LastName"));

        List<MangaGenre> genres = new ArrayList<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        Locale locale = new Locale("en");

        when(authorService.findAll()).thenReturn(authors);
        when(mangaGenreService.findAll()).thenReturn(genres);

        assertAll(
                () -> mockMvc.perform(get("/admin/addOrUpdateManga").flashAttr("locale", locale))
                        .andExpect(status().isOk()).andExpect(view().name("views/manga-form"))
                        .andExpect(model().attribute("mangaDTO", new MangaDTO()))
                        .andExpect(model().attribute("authorDTO", new AuthorDTO()))
                        .andExpect(model().attribute("locale", locale)).andExpect(model().attribute("authors", authors))
                        .andExpect(model().attribute("genres", genres)),
                () -> verify(authorService, times(1)).findAll(), () -> verify(mangaGenreService, times(1)).findAll());
    }

    @Test
    void when_load_edit_manga_page_should_show_manga_form() throws MangaNotFoundException {

        Long mangaId = 1L;

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
                .description("English description").build();
        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
                .description("Polish description").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Set<Author> authors = new HashSet<>();
        Author author = new Author("FirstName LastName");
        authors.add(author);

        Set<MangaGenre> genres = new HashSet<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        MangaDTO mangaDTO = MangaDTO.builder().id(mangaId).enTranslation(mangaTranslationEnDTO)
                .plTranslation(mangaTranslationPlDTO).image(image).authors(authors).genres(genres).build();

        List<Author> authorsDB = new ArrayList<>();
        authors.add(author);

        List<MangaGenre> genresDB = new ArrayList<>();
        genresDB.add(new MangaGenre("genre en", "genre pl"));

        Locale locale = new Locale("en");

        when(authorService.findAll()).thenReturn(authorsDB);
        when(mangaGenreService.findAll()).thenReturn(genresDB);
        when(mangaService.getMangaDTOById(mangaId)).thenReturn(mangaDTO);

        assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateManga/{id}", mangaId).flashAttr("locale", locale))
                        .andExpect(status().isOk()).andExpect(view().name("views/manga-form"))
                        .andExpect(model().attribute("mangaDTO", mangaDTO))
                        .andExpect(model().attribute("authorDTO", new AuthorDTO()))
                        .andExpect(model().attribute("authors", authorsDB)).andExpect(model().attribute("genres", genresDB))
                        .andExpect(model().attribute("locale", locale)), () -> verify(authorService, times(1)).findAll(),
                () -> verify(mangaGenreService, times(1)).findAll());
    }

    @Test
    void when_add_manga_with_correct_data_should_save_manga()
            throws LanguageNotFoundException, MangaNotFoundException, IOException {

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
                .description("English description").build();
        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
                .description("Polish description").build();

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        Set<Author> authors = new HashSet<>();
        Author authorExpected = new Author("FirstName LastName");
        authors.add(authorExpected);

        Set<MangaGenre> genres = new HashSet<>();
        MangaGenre genreExpected = new MangaGenre("genre en", "genre pl");
        genres.add(genreExpected);

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .image(image).authors(authors).genres(genres).build();

        MangaTranslation mangaTranslationEnExpected = MangaTranslation.builder().title("English title")
                .description("English description").build();
        MangaTranslation mangaTranslationPlExpected = MangaTranslation.builder().title("Polish title")
                .description("Polish description").build();

        mangaDTO.setAuthors(authors);

        Manga mangaExpected = new Manga();
        mangaExpected.addAuthor(authorExpected);
        mangaExpected.addTranslation(mangaTranslationEnExpected);
        mangaExpected.addTranslation(mangaTranslationPlExpected);
        mangaExpected.setImage(image.getBytes());
        mangaExpected.addGenre(genreExpected);

        Locale locale = new Locale("en");

        when(mangaTranslationService.addOrUpdate(mangaDTO)).thenReturn(mangaExpected);

        assertAll(
                () -> mockMvc
                        .perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .flashAttr("mangaDTO", mangaDTO).flashAttr("locale", locale))
                        .andExpect(status().is3xxRedirection()).andExpect(model().hasNoErrors())
                        .andExpect(redirectedUrl("/admin/addOrUpdateManga?locale=" + locale.getLanguage())),
                () -> verify(mangaTranslationService, times(1)).addOrUpdate(mangaDTO),
                () -> verify(mangaService, times(1)).addOrUpdate(mangaDTO, mangaExpected));
    }

    @Test
    void when_add_or_edit_manga_with_blank_fields_and_authors_selected_should_show_manga_form() {

        String englishTitle = "";
        String englishDescription = "";
        String polishTitle = "";
        String polishDescription = "";

        MockMultipartFile image = null;

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("FirstName LastName"));

        List<MangaGenre> genres = new ArrayList<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        when(authorService.findAll()).thenReturn(authors);
        when(mangaGenreService.findAll()).thenReturn(genres);

        Set<Author> mangaAuthors = new HashSet<>();
        mangaAuthors.add(new Author("FirstName LastName"));

        Set<MangaGenre> mangaGenres = new HashSet<>();
        mangaGenres.add(new MangaGenre("genre en", "genre pl"));

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(englishTitle)
                .description(englishDescription).build();
        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(polishTitle)
                .description(polishDescription).build();

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .image(image).authors(mangaAuthors).genres(mangaGenres).build();

        Locale locale = new Locale("en");

        assertAll(
                () -> mockMvc
                        .perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .flashAttr("mangaDTO", mangaDTO).flashAttr("locale", locale))
                        .andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
                        .andExpect(model().attribute("mangaDTO", mangaDTO))
                        .andExpect(model().attribute("authorDTO", new AuthorDTO()))
                        .andExpect(model().attribute("authors", authors)).andExpect(model().attribute("genres", genres))
                        .andExpect(model().attribute("locale", locale))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.title"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.description"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.title"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.description"))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("enTranslation", hasProperty("title", is(englishTitle)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("enTranslation", hasProperty("description", is(englishDescription)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("plTranslation", hasProperty("title", is(polishTitle)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("plTranslation", hasProperty("description", is(polishDescription)))))
                        .andExpect(model().attribute("mangaDTO", hasProperty("authors", is(mangaAuthors))))
                        .andExpect(model().attribute("mangaDTO", hasProperty("genres", is(mangaGenres)))),
                () -> verify(mangaTranslationService, never()).addOrUpdate(mangaDTO),
                () -> verify(authorService, times(1)).findAll(), () -> verify(mangaGenreService, times(1)).findAll());
    }

    @Test
    void when_add_or_edit_manga_with_long_data_and_authors_not_selected_should_show_manga_form() {

        String title = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
        String description = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%".repeat(30);
        String englishTitle = title;
        String englishDescription = description;
        String polishTitle = title;
        String polishDescription = description;

        MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("FirstName LastName"));

        List<MangaGenre> genres = new ArrayList<>();
        genres.add(new MangaGenre("genre en", "genre pl"));

        when(authorService.findAll()).thenReturn(authors);
        when(mangaGenreService.findAll()).thenReturn(genres);

        Set<Author> mangaAuthors = new HashSet<>();

        Set<MangaGenre> mangaGenres = new HashSet<>();

        MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(englishTitle)
                .description(englishDescription).build();
        MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(polishTitle)
                .description(polishDescription).build();

        MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
                .image(image).authors(mangaAuthors).genres(mangaGenres).build();

        Locale locale = new Locale("en");

        assertAll(
                () -> mockMvc
                        .perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .flashAttr("mangaDTO", mangaDTO).flashAttr("locale", locale))
                        .andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
                        .andExpect(model().attribute("mangaDTO", mangaDTO))
                        .andExpect(model().attribute("authorDTO", new AuthorDTO()))
                        .andExpect(model().attribute("authors", authors)).andExpect(model().attribute("genres", genres))
                        .andExpect(model().attribute("locale", locale))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "authors"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "genres"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.title"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.description"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.title"))
                        .andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.description"))
                        .andExpect(model().attribute("mangaDTO", hasProperty("authors", is(mangaAuthors))))
                        .andExpect(model().attribute("mangaDTO", hasProperty("genres", is(mangaGenres))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("enTranslation", hasProperty("title", is(englishTitle)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("enTranslation", hasProperty("description", is(englishDescription)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("plTranslation", hasProperty("title", is(polishTitle)))))
                        .andExpect(model().attribute("mangaDTO",
                                hasProperty("plTranslation", hasProperty("description", is(polishDescription))))),
                () -> verify(mangaTranslationService, never()).addOrUpdate(mangaDTO),
                () -> verify(authorService, times(1)).findAll(), () -> verify(mangaGenreService, times(1)).findAll());
    }

    @Test
    void when_delete_manga_should_delete_manga_and_redirect() {

        Long mangaId = 1L;

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/admin/deleteManga/{id}", mangaId)
                .header("Referer", "/");

        assertAll(
                () -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
                () -> verify(mangaService, times(1)).deleteManga(mangaId));
    }
}
