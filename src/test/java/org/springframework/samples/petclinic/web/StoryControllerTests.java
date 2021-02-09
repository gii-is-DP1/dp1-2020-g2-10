package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;

import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.story.PublishedStoryUpdateExeption;
import org.springframework.samples.petclinic.service.exceptions.story.UnauthorizedStoryUpdateException;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers= {StoryController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class) 
class StoryControllerTests {

	private static final int TEST_AUTHOR_ID = 1;

	private static final int TEST_STORY_ID = 1;
	

	@Autowired
	private StoryController storyController;

	@MockBean
	private StoryService storyService;

	@MockBean
	private AuthorService authorService;
    
    @MockBean
    private UserService userService;

	@Autowired
	private MockMvc mockMvc;


	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		
		Timestamp updatedDate = new Timestamp(12000000000000l); 

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcione");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(updatedDate);
		story.setUrlCover("/resources/images/author-pictures/author1.jpg");
		given(this.authorService.findAuthorById(TEST_AUTHOR_ID)).willReturn(new Author());
		given(this.storyService.findStoryById(TEST_STORY_ID)).willReturn(new Story());
		Story storyMock2 = new Story();
		storyMock2.setUpdatedDate(updatedDate);
		storyMock2.setAuthor(new Author());
		given(this.storyService.createStory()).willReturn(storyMock2 );
	}
	
	//Pruebas de controlador HU-01: Listado y mostrado de story
	
	@WithMockUser(value = "spring")
    @Test
	void testListStory() throws Exception {
		mockMvc.perform(get("/stories/list", TEST_STORY_ID))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("stories"))
				.andExpect(view().name("stories/storiesList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowStory() throws Exception {
	    mockMvc.perform(get("/stories/{storyId}/show", TEST_STORY_ID))
	    		.andExpect(status().isOk())
	            .andExpect(model().attributeExists("story"))
	            .andExpect(view().name("stories/showStory"));
	}
	
	//Pruebas HU02
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/stories/new")).andExpect(status().isOk())
				.andExpect(view().name("stories/createOrUpdateStoryForm"))
				.andExpect(model().attributeExists("story"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/stories/new")
							.with(csrf())
							.param("id", "20")
							.param("version", "0")
							.param("authorId", "1")
							.param("updatedDate", "2020/10/10 10:10")
							.param("title", "La mejor historia jamás contada")
							.param("description", "La mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor")
							.param("isAdult", "true")
							.param("genre", "TALE")
							.param("storyStatus", "DRAFT"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/list"));
	}

	//Hay que arreglar
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stories/new")
				.with(csrf())
				.param("id", "20")
				.param("version", "0")
				.param("authorId", "1")
				.param("updatedDate", "2020/10/10 10:10")
				.param("title", "La mejor historia jamás contada")
				.param("isAdult", "true")
				.param("genre", "TALE")
				.param("storyStatus", "DRAFT"))
				.andExpect(model().attributeHasErrors("story"))
				.andExpect(status().isOk())
				.andExpect(view().name("stories/createOrUpdateStoryForm"));
	}
	
	
	
	@WithMockUser(value = "spring")
    //@Test
	void testInitEditCreationForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/edit", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(view().name("stories/createOrUpdateStoryForm"));
	}
	
	@WithMockUser(value = "author1", authorities = "author")
    @Test
    @Disabled

	void testEditCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/edit", TEST_STORY_ID)
							.with(csrf())
							.param("id", "1")
							.param("version", "5")
							.param("authorId", "1")
							.param("updatedDate", "2020/10/10 10:10")
							.param("title", "La mejor historia jamás contada")
							.param("description", "La mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor"
									+ ", la mejor, la mejor, la mejor, la mejor, la mejor, la mejor, la mejor")
							.param("isAdult", "true")
							.param("genre", "TALE")
							.param("storyStatus", "DRAFT"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/list"));
	}

	@WithMockUser(value = "spring")
    @Test
    @Disabled
	void testProcessEditCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/new", TEST_STORY_ID)
				.with(csrf())
				.param("id", "1")
				.param("version", "5")
				.param("authorId", "1")
				.param("updatedDate", "2020/10/10 10:10")
				.param("title", "")
				.param("isAdult", "true")
				.param("genre", "TALE")
				.param("storyStatus", "DRAFT"))
				.andExpect(model().attributeHasErrors("story"))
				.andExpect(status().isOk())
				.andExpect(view().name("stories/createOrUpdateStoryForm"));
	}
	@Nested
	@DisplayName("H3: Update story")
	class StoryServiceTestsH3{
		
		/* === H3 Actualizar historia (Carlos - carcabcol) ========================== */
		
		private static final int DUMMY_DRAFT_STORY_ID = 1;
		private static final int AUTHORIZED_AUTHOR_ID = 1;
		private static final int UNAUTHORIZED_AUTHOR_ID = 2;
		
		private Story getDummyDraftStory() {
			
			Story dummyDraftStory = new Story();
			dummyDraftStory.setId(DUMMY_DRAFT_STORY_ID);
			dummyDraftStory.setVersion(0);
			
			String dummyDraftStoryTitle = "Lorem ipsum";
			String dummyDraftStoryDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
					+ "Vestibulum at blandit dolor, at laoreet nulla. Donec nibh nisi.";
			dummyDraftStory.setTitle(dummyDraftStoryTitle);
			dummyDraftStory.setDescription(dummyDraftStoryDescription);
			dummyDraftStory.setStoryStatus(StoryStatus.DRAFT);
			
			User userAuthor1 = new User();
			userAuthor1.setUsername("author1");
			Author author1 = new Author();
			author1.setId(AUTHORIZED_AUTHOR_ID);
			author1.setUser(userAuthor1);
			
			dummyDraftStory.setAuthor(author1);
			
			return dummyDraftStory;
		}
		
		@BeforeEach
		void resetMocks() {
			// If not, stubs interactions are saved and tests fail
			Mockito.reset(storyService, authorService, userService);
		}

		@WithMockUser(value = "spring")
	    @Test
	    @DisplayName("GET editStory form")
		void testInitEditionForm() throws Exception {
			Story dummyDraftStory = getDummyDraftStory();
			given(storyService.findStory(DUMMY_DRAFT_STORY_ID)).willReturn(dummyDraftStory);
			// 2. Act
			mockMvc.perform(get("/stories/{storyId}/edit", DUMMY_DRAFT_STORY_ID))
			// 3. Assert
			.andExpect(status().isOk())
			.andExpect(view().name("stories/createOrUpdateStoryForm"))
			.andExpect(model().attributeExists("story"))
			.andExpect(model().attributeExists("genres"))
			.andExpect(model().attributeExists("storyStatuses"))
			.andExpect(model().attribute("story", Matchers.samePropertyValuesAs(dummyDraftStory)));
		}
		
		// H3+E1 Actualizar historia
		@Test
		@WithMockUser(value = "spring")
		@DisplayName("H3+E1: Should update DRAFT story")
		void shouldUpdateDraftStoryTitle() throws Exception {
			// 1. Arrange
			Story dummyDraftStory = getDummyDraftStory();
			given(storyService.findStoryById(DUMMY_DRAFT_STORY_ID)).willReturn(dummyDraftStory);
			
			// 2. Act
			mockMvc.perform(post("/stories/{storyId}/edit", DUMMY_DRAFT_STORY_ID)
					.with(csrf())
					.param("id", getDummyDraftStory().getId().toString())
					.param("version", "0")
					.param("authorId", Integer.toString(AUTHORIZED_AUTHOR_ID))
					.param("title", getDummyDraftStory().getTitle()+" (updated)")
					.param("description", getDummyDraftStory().getDescription())
					.param("dedication", "")
					.param("isAdult", "false")
					.param("genre", Genre.NOVEL.toString())
					.param("storyStatus", StoryStatus.DRAFT.toString())
					.param("updatedDate", "2021/01/31 10:00"))
			// 3. Assert
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("messageType","success"))
			.andExpect(view().name("redirect:/stories/list"));
			
			Mockito.verify(storyService).saveStory(any(Story.class));
		}
		
		// H3+E2 Publicar historia inicialmente en borrador
		@Test
		@WithMockUser(value = "spring")
		@DisplayName("H3+E2: Should publish DRAFT story")
		void shouldPublishDraftStory() throws Exception {
			// 1. Arrange
			Story dummyDraftStory = getDummyDraftStory();
			given(storyService.findStoryById(DUMMY_DRAFT_STORY_ID)).willReturn(dummyDraftStory);
			
			// 2. Act
			mockMvc.perform(post("/stories/{storyId}/edit", DUMMY_DRAFT_STORY_ID)
					.with(csrf())
					.param("id", getDummyDraftStory().getId().toString())
					.param("version", "0")
					.param("authorId", Integer.toString(AUTHORIZED_AUTHOR_ID))
					.param("title", getDummyDraftStory().getTitle())
					.param("description", getDummyDraftStory().getDescription())
					.param("dedication", "")
					.param("isAdult", "false")
					.param("genre", Genre.NOVEL.toString())
					.param("storyStatus", StoryStatus.PUBLISHED.toString())
					.param("updatedDate", "2021/01/31 10:00"))
			// 3. Assert
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("messageType","success"))
			.andExpect(view().name("redirect:/stories/list"));
			
			Mockito.verify(storyService).saveStory(any(Story.class));
		}
		
		// H3-E1 No actualizar un borrador de historia de otro autor
		@Test
		@WithMockUser(value = "spring")
		@DisplayName("H3-E1: Forbid update DRAFT story by other author")
		void houldNotUpdateDraftStory() throws Exception {
			// 1. Arrange
			Story dummyDraftStory = getDummyDraftStory();
			given(storyService.findStoryById(DUMMY_DRAFT_STORY_ID)).willReturn(dummyDraftStory);
			doThrow(new UnauthorizedStoryUpdateException("message"))
				.when(storyService).saveStory(any(Story.class));
			// 2. Act
			mockMvc.perform(post("/stories/{storyId}/edit", DUMMY_DRAFT_STORY_ID)
					.with(csrf())
					.param("id", getDummyDraftStory().getId().toString())
					.param("version", "0")
					.param("authorId", Integer.toString(AUTHORIZED_AUTHOR_ID))
					.param("title", getDummyDraftStory().getTitle()+" (updated)")
					.param("description", getDummyDraftStory().getDescription())
					.param("dedication", "")
					.param("isAdult", "false")
					.param("genre", Genre.NOVEL.toString())
					.param("storyStatus", StoryStatus.PUBLISHED.toString())
					.param("updatedDate", "2021/01/31 10:00"))
			// 3. Assert
			.andExpect(status().is2xxSuccessful())
			.andExpect(model().attributeExists("message"))
			.andExpect(model().attribute("messageType","danger"))
			.andExpect(view().name("errors/error403"));
			
			Mockito.verify(storyService).saveStory(any(Story.class));
		}
		
		// H3-E2 No actualizar una historia ya publicada
		@Test
		@WithMockUser(value = "spring")
		@DisplayName("H3-E2: Forbid update of a PUBLISHED story")
		void shouldNotUpdatePublishedStory() throws Exception {
			// 1. Arrange
			Story dummyDraftStory = getDummyDraftStory();
			given(storyService.findStoryById(DUMMY_DRAFT_STORY_ID)).willReturn(dummyDraftStory);
			doThrow(new PublishedStoryUpdateExeption())
				.when(storyService).saveStory(any(Story.class));
			// 2. Act
			mockMvc.perform(post("/stories/{storyId}/edit", DUMMY_DRAFT_STORY_ID)
					.with(csrf())
					.param("id", getDummyDraftStory().getId().toString())
					.param("version", "0")
					.param("authorId", Integer.toString(AUTHORIZED_AUTHOR_ID))
					.param("title", getDummyDraftStory().getTitle()+" (updated)")
					.param("description", getDummyDraftStory().getDescription())
					.param("dedication", "")
					.param("isAdult", "false")
					.param("genre", Genre.NOVEL.toString())
					.param("storyStatus", StoryStatus.PUBLISHED.toString())
					.param("updatedDate", "2021/01/31 10:00"))
			// 3. Assert
			.andExpect(status().is3xxRedirection())
			.andExpect(flash().attributeExists("message"))
			.andExpect(flash().attribute("messageType","danger"))
			.andExpect(view().name("redirect:/stories/list"));
			
			Mockito.verify(storyService).saveStory(any(Story.class));
		}
		
		
	}
	//Test de controlador positivo HU-04-E1
	
	
	@WithMockUser(value = "spring")
    @Test
    void testDeleteDraftStory() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
        		.get("/stories/{storyId}/delete", TEST_STORY_ID))
            .andExpect(model().attributeDoesNotExist("story"))
            .andExpect(view().name("redirect:/stories/list"));
    }
	
	//No hay caso negativo negativo al ser un borrado
	
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
//				.andExpect(status().isOk())
//				.andExpect(view().name("pets/createOrUpdatePetForm"))
//				.andExpect(model().attributeExists("pet"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID).with(csrf()).param("name", "Betty")
//				.param("type", "hamster").param("birthDate", "2015/02/12")).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/owners/{ownerId}"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).with(csrf())
//				.param("name", "Betty").param("birthDate", "2015/02/12"))
//				.andExpect(model().attributeHasNoErrors("owner")).andExpect(model().attributeHasErrors("pet"))
//				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitUpdateForm() throws Exception {
//		mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
//				.andExpect(status().isOk()).andExpect(model().attributeExists("pet"))
//				.andExpect(view().name("pets/createOrUpdatePetForm"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateFormSuccess() throws Exception {
//		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).with(csrf())
//				.param("name", "Betty").param("type", "hamster").param("birthDate", "2015/02/12"))
//				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/owners/{ownerId}"));
//	}
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateFormHasErrors() throws Exception {
//		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).with(csrf())
//				.param("name", "Betty").param("birthDate", "2015/02/12"))
//				.andExpect(model().attributeHasNoErrors("owner")).andExpect(model().attributeHasErrors("pet"))
//				.andExpect(status().isOk()).andExpect(view().name("pets/createOrUpdatePetForm"));
//	}
}
