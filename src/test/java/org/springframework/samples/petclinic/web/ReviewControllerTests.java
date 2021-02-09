package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import lombok.extern.slf4j.Slf4j;


@WebMvcTest(controllers = {ReviewController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
//includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@Slf4j
class ReviewControllerTests {

	
	private static final int TEST_REVIEW_ID = 1;

	private static final int TEST_STORY_ID = 1;
	

	@Autowired
	private ReviewController reviewController;


	@MockBean
	private ReviewService reviewService;
        
    @MockBean
	private StoryService storyService;
    
    @MockBean
	private AuthorService authorService;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private AlexandriaErrorController alexandriaErrorController;

    @Autowired
    private AlexandriaControllerAdvice alexandriaControllerAdvice;
    
    @MockBean
    private UserService userService;

	@BeforeEach
	void setup() {
		Story story = new Story();
		story.setId(TEST_STORY_ID);
		Review review = new Review();
		review.setPublicationDate(new Date());
		review.setStory(story);
		Author author = new Author();
		author.setId(1);
		given(this.storyService.findStoryById(TEST_STORY_ID)).willReturn(new Story());
		given(this.reviewService.findReviewById(TEST_REVIEW_ID)).willReturn(new Review());
		given(this.reviewService.createReview(story)).willReturn(review);
		given(this.authorService.getPrincipal()).willReturn(author);
	}

	@WithMockUser(value = "spring")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/reviews/new", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(view().name("review/editReview")).andExpect(model().attributeExists("review"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		Review r1 = new Review();
		r1.setPublicationDate(new Date());
		mockMvc.perform(post("/stories/{storyId}/reviews/new", TEST_STORY_ID)
							.with(csrf())
							.param("title", "Me ha gustado un huevo")
							.param("text", "No te haces a la idea de lo que he disfrutado esta maravillosa historia de amor, celos y venganza.")
							.param("rating", "4")
							.param("publicationDate", "2020/10/10 10:10")
							.param("story.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/{storyId}/show"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/reviews/new", TEST_STORY_ID)
							.with(csrf())
							.param("title", "Me ha gustado un huevo")
							.param("text", "No te haces a la idea de lo que he disfrutado esta maravillosa historia de amor, celos y venganza."))
				.andExpect(model().attributeHasNoErrors("story"))
				.andExpect(model().attributeHasErrors("review"))
				.andExpect(status().isOk())
				.andExpect(view().name("review/editReview"));
	}
	
	
}
