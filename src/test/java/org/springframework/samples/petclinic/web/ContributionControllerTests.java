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
import org.springframework.samples.petclinic.model.Contribution;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ContributionService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {ContributionController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class) 
class ContributionControllerTests {

	
	private static final int TEST_CONTRIBUTION_ID = 1;

	private static final int TEST_STORY_ID = 1;
	
	private static final int TEST_AUTHOR_ID = 1;
	
	@Autowired
	private AlexandriaErrorController alexandriaErrorController;
	@Autowired
	private AlexandriaControllerAdvice alexandriaControllerAdvice;
	@MockBean
	private UserService userService;
	
	
	@Autowired
	private ContributionController contributionController;


	@MockBean
	private ContributionService contributionService;
        
    @MockBean
	private StoryService storyService;
    
    @MockBean
	private AuthorService authorService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		Author author1 = new Author();
		author1.setId(1);
		Author author2 = new Author();
		author2.setId(2);
		Story story = new Story();
		story.setId(1);
		story.setAuthor(author1);
		given(authorService.findAuthorById(author2.getId())).willReturn(author2);
		given(storyService.findStory(story.getId())).willReturn(story);
	}

	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/contribution/new", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(view().name("contribution/editContribution")).andExpect(model().attributeExists("contribution"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/contribution/new", TEST_STORY_ID)
							.with(csrf())
							.param("author.id", "1")
							.param("contributionType", "COAUTHOR"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/list"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/contribution/new", TEST_STORY_ID)
							.with(csrf())
							.param("author.id", "1")
							.param("contribucion", ""))
				.andExpect(model().attributeHasErrors("contribution"))
				.andExpect(status().isOk())
				.andExpect(view().name("contribution/editContribution"));
	}
	
	
}
