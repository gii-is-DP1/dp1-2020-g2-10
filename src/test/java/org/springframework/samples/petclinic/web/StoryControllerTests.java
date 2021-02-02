package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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
	
	@Autowired
    private AlexandriaErrorController alexandriaErrorController;

    @Autowired
    private AlexandriaControllerAdvice alexandriaControllerAdvice;
    
    @MockBean
    private UserService userService;

	@Autowired
	private MockMvc mockMvc;


	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		
		Timestamp updatedDate = new Timestamp(120, 9, 12, 15, 00, 0, 0); 

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
	}
	
	//Pruebas de controlador HU-01: Listado y mostrado de story
	
	@WithMockUser(value = "spring")
    @Test
	void testListStory() throws Exception {
		mockMvc.perform(get("/stories/list", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("stories")).andExpect(view().name("stories/storiesList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowStory() throws Exception {
	    mockMvc.perform(get("/stories/{storyId}/show", TEST_STORY_ID)).andExpect(status().isOk())
	            .andExpect(model().attributeExists("story")).andExpect(view().name("stories/showStory"));
	}
	
//	
//	
//
//	@WithMockUser(value = "spring")
//	@Test
//	void testInitCreationForm() throws Exception {
//		mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID)).andExpect(status().isOk())
//				.andExpect(view().name("pets/createOrUpdatePetForm")).andExpect(model().attributeExists("pet"));
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
