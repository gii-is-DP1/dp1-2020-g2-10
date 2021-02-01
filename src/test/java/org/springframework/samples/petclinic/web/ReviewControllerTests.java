package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = ReviewController.class, excludeAutoConfiguration = SecurityConfiguration.class)
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

	@BeforeEach
	void setup() {
		given(this.storyService.findStoryById(TEST_STORY_ID)).willReturn(new Story());
		given(this.reviewService.findReviewById(TEST_REVIEW_ID)).willReturn(new Review());
	}

	@WithMockUser(value = "author1")
    @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/reviews/new", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(view().name("reviews/editReview")).andExpect(model().attributeExists("review"));
	}

	@WithMockUser(value = "author1")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/reviews/new", TEST_STORY_ID)
							.with(csrf())
							.param("title", "Me ha gustado un huevo")
							.param("text", "No te haces a la idea de lo que he disfrutado esta maravillosa historia de amor, celos y venganza.")
							.param("rating", "4"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/{storyId}"));
	}

	@WithMockUser(value = "author1")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/reviews/new", TEST_STORY_ID)
							.with(csrf())
							.param("title", "Me ha gustado un huevo")
							.param("rating", "4"))
				.andExpect(model().attributeHasNoErrors("story"))
				.andExpect(model().attributeHasErrors("review"))
				.andExpect(status().isOk())
				.andExpect(view().name("review/editReview"));
	}
	
	
}
