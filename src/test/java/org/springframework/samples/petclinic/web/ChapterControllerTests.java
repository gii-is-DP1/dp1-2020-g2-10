package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link VisitController}
 *
 * @author Colin But
 */
@WebMvcTest(controllers = ChapterController.class,
			excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
			excludeAutoConfiguration= SecurityConfiguration.class)
class ChapterControllerTests {


	private static final int TEST_AUTHOR_ID = 1;

	private static final int TEST_STORY_ID = 1;

	
	@MockBean
	private ChapterService chapterService;
	
	@MockBean
	private StoryService storyService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		
		Story s = new Story();
		
		
		Chapter ch = new Chapter();
		ch.setIndex(1);
		ch.setTitle("Travesía");
		ch.setText("Deambulando en las noches de mi páramo" + "Turista en mi hipotálamo");
		ch.setIsPublished(true);
		ch.setStory(s);
	}

		@WithMockUser(value = "spring")
	   @Test
	   
		void testInitNewChapterForm() throws Exception {
			mockMvc.perform(get("/authors/{authorId}/stories/{storyId}/chapter/new",TEST_AUTHOR_ID, TEST_STORY_ID)).andExpect(status().isOk())
					.andExpect(view().name("exception"));
		}
	      
	    
	   
		@WithMockUser(value = "spring")
		@Test
		void testProcessCreationFormSuccess() throws Exception {
			mockMvc.perform(post("/authors/{authorId}/stories/{storyId}/chapter/new", TEST_AUTHOR_ID, TEST_STORY_ID)
								.with(csrf())
								.param("index", "1")
								.param("title", "Dama de las Nieves")
								.param("text", "Tormenta serena en tus ojos de huracán, lo más bello visto jamás" +
												"Nadie puede adulterar tu belleza, aprecio tu corazón y tu inteligencia" +
												"Cúrame, soy el paciente que aguarda con paciencia.")
								.param("isPublished", "true")
								.param("story.id", "1"))
			.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/authors/{authorId}/stories/{storyId}/chapter"));;
		}


}
