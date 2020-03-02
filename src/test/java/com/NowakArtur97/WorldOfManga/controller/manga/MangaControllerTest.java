package com.NowakArtur97.WorldOfManga.controller.manga;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.NowakArtur97.WorldOfManga.dto.AuthorDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaDTO;
import com.NowakArtur97.WorldOfManga.dto.MangaTranslationDTO;
import com.NowakArtur97.WorldOfManga.exception.LanguageNotFoundException;
import com.NowakArtur97.WorldOfManga.exception.MangaNotFoundException;
import com.NowakArtur97.WorldOfManga.model.Author;
import com.NowakArtur97.WorldOfManga.model.Manga;
import com.NowakArtur97.WorldOfManga.model.MangaTranslation;
import com.NowakArtur97.WorldOfManga.service.api.AuthorService;
import com.NowakArtur97.WorldOfManga.service.api.MangaService;
import com.NowakArtur97.WorldOfManga.service.api.MangaTranslationService;
import com.NowakArtur97.WorldOfManga.validation.manga.MangaValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("Manga Controller Tests")
@Tag("MangaController_Tests")
public class MangaControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private MangaController mangaController;

	@Mock
	private MangaService mangaService;

	@Mock
	private MangaTranslationService mangaTranslationService;

	@Mock
	private MangaValidator mangaValidator;

	@Mock
	private AuthorService authorService;

	@BeforeEach
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(mangaController).build();
	}

	@Test
	@DisplayName("when load add manga page")
	public void when_load_add_manga_page_should_show_manga_form() {

		List<Author> authors = new ArrayList<>();
		authors.add(new Author("FirstName LastName"));

		when(authorService.findAll()).thenReturn(authors);

		assertAll(() -> mockMvc.perform(get("/admin/addOrUpdateManga")).andExpect(status().isOk())
				.andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", new MangaDTO()))
				.andExpect(model().attribute("authorDTO", new AuthorDTO()))
				.andExpect(model().attribute("authors", authors)), () -> verify(authorService, times(1)).findAll());
	}

	@Test
	@DisplayName("when load edit manga page")
	public void when_load_edit_manga_page_should_show_manga_form() throws MangaNotFoundException {

		Long mangaId = 1L;

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English description").build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Set<Author> authors = new HashSet<>();
		Author author = new Author("FirstName LastName");
		authors.add(author);

		MangaDTO mangaDTO = MangaDTO.builder().id(mangaId).enTranslation(mangaTranslationEnDTO)
				.plTranslation(mangaTranslationPlDTO).image(image).authors(authors).build();

		List<Author> authorsDB = new ArrayList<>();
		authors.add(author);

		when(authorService.findAll()).thenReturn(authorsDB);
		when(mangaService.getMangaDTOById(mangaId)).thenReturn(mangaDTO);

		assertAll(
				() -> mockMvc.perform(get("/admin/addOrUpdateManga/{id}", mangaId)).andExpect(status().isOk())
						.andExpect(view().name("views/manga-form")).andExpect(model().attribute("mangaDTO", mangaDTO))
						.andExpect(model().attribute("authorDTO", new AuthorDTO()))
						.andExpect(model().attribute("authors", authorsDB)),
				() -> verify(authorService, times(1)).findAll());
	}

	@Test
	@DisplayName("when add manga with correct data")
	public void when_add_manga_with_correct_data_should_save_manga()
			throws LanguageNotFoundException, MangaNotFoundException, IOException {

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title("English title")
				.description("English description").build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title("Polish title")
				.description("Polish description").build();

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		Set<Author> authors = new HashSet<>();
		Author authorExpected = new Author("FirstName LastName");
		authors.add(authorExpected);

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).authors(authors).build();

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

		when(mangaTranslationService.addOrUpdate(mangaDTO)).thenReturn(mangaExpected);

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("mangaDTO", mangaDTO))
						.andExpect(status().is3xxRedirection()).andExpect(model().hasNoErrors())
						.andExpect(redirectedUrl("/admin/addOrUpdateManga")),
				() -> verify(mangaTranslationService, times(1)).addOrUpdate(mangaDTO),
				() -> verify(mangaService, times(1)).addOrUpdate(mangaDTO, mangaExpected));
	}

	@Test
	@DisplayName("when add or edit manga with blank fields and authors selected")
	public void when_add_or_edit_manga_with_blank_fields_and_authors_selected_should_show_manga_form() {

		String englishTitle = "";
		String englishDescription = "";
		String polishTitle = "";
		String polishDescription = "";

		MockMultipartFile image = null;

		List<Author> authors = new ArrayList<>();
		authors.add(new Author("FirstName LastName"));

		when(authorService.findAll()).thenReturn(authors);

		Set<Author> mangaAuthors = new HashSet<>();
		mangaAuthors.add(new Author("FirstName LastName"));

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(englishTitle)
				.description(englishDescription).build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(polishTitle)
				.description(polishDescription).build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).authors(mangaAuthors).build();

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("mangaDTO", mangaDTO))
						.andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
						.andExpect(model().attribute("mangaDTO", mangaDTO))
						.andExpect(model().attribute("authorDTO", new AuthorDTO()))
						.andExpect(model().attribute("authors", authors))
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
						.andExpect(model().attribute("mangaDTO", hasProperty("authors", is(mangaAuthors)))),
				() -> verify(mangaTranslationService, never()).addOrUpdate(mangaDTO),
				() -> verify(authorService, times(1)).findAll());
	}

	@Test
	@DisplayName("when add or edit manga with long data and authors not selected")
	public void when_add_or_edit_manga_with_long_data_and_authors_not_selected_should_show_manga_form() {

		String title = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%";
		String description = "asdfghjklpasdfghjklpasdfghjklpasdfghjklpasdfghjklp!@#$%".repeat(30);
		String englishTitle = title;
		String englishDescription = description;
		String polishTitle = title;
		String polishDescription = description;

		MockMultipartFile image = new MockMultipartFile("image.jpg", "file bytes".getBytes());

		List<Author> authors = new ArrayList<>();
		authors.add(new Author("FirstName LastName"));

		when(authorService.findAll()).thenReturn(authors);

		Set<Author> mangaAuthors = new HashSet<>();

		MangaTranslationDTO mangaTranslationEnDTO = MangaTranslationDTO.builder().title(englishTitle)
				.description(englishDescription).build();
		MangaTranslationDTO mangaTranslationPlDTO = MangaTranslationDTO.builder().title(polishTitle)
				.description(polishDescription).build();

		MangaDTO mangaDTO = MangaDTO.builder().enTranslation(mangaTranslationEnDTO).plTranslation(mangaTranslationPlDTO)
				.image(image).authors(mangaAuthors).build();

		assertAll(
				() -> mockMvc
						.perform(post("/admin/addOrUpdateManga").contentType(MediaType.APPLICATION_FORM_URLENCODED)
								.flashAttr("mangaDTO", mangaDTO))
						.andExpect(status().isOk()).andExpect(forwardedUrl("views/manga-form"))
						.andExpect(model().attribute("mangaDTO", mangaDTO))
						.andExpect(model().attribute("authorDTO", new AuthorDTO()))
						.andExpect(model().attribute("authors", authors))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "authors"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.title"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "enTranslation.description"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.title"))
						.andExpect(model().attributeHasFieldErrors("mangaDTO", "plTranslation.description"))
						.andExpect(model().attribute("mangaDTO", hasProperty("authors", is(mangaAuthors))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("enTranslation", hasProperty("title", is(englishTitle)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("enTranslation", hasProperty("description", is(englishDescription)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("plTranslation", hasProperty("title", is(polishTitle)))))
						.andExpect(model().attribute("mangaDTO",
								hasProperty("plTranslation", hasProperty("description", is(polishDescription))))),
				() -> verify(mangaTranslationService, never()).addOrUpdate(mangaDTO),
				() -> verify(authorService, times(1)).findAll());
	}

	@Test
	@DisplayName("when delete manga")
	public void when_delete_manga_should_delete_manga_and_redirect() {

		Long mangaId = 1L;

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/admin/deleteManga/{id}", mangaId)
				.header("Referer", "/");

		assertAll(
				() -> mockMvc.perform(mockRequest).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/")),
				() -> verify(mangaService, times(1)).deleteManga(mangaId));
	}
}
