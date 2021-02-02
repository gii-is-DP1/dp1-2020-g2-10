package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(controllers= {ChapterController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ChapterControllerTests {
	
	private static final int TEST_CHAPTER_ID    = 1;
	private static final int TEST_STORY_ID = 1;

	@Autowired
	private ChapterController chapterController;
	
	@Autowired
	private AlexandriaErrorController alexandriaErrorController;
	
	@Autowired
	private AlexandriaControllerAdvice alexandriaControllerAdvice;
	
	@MockBean
	private ChapterService chapterService;
	
	@MockBean
	private StoryService storyService;
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {

		given(this.storyService.findStoryById(TEST_STORY_ID)).willReturn(new Story());
		given(this.chapterService.findChapterById(TEST_CHAPTER_ID)).willReturn(new Chapter());

	}
	
	@WithMockUser(value = "spring")
    @Test
	void testListChapter() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/chapters", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("chapters")).andExpect(view().name("chapters/listChapters"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowChapter() throws Exception {
	    mockMvc.perform(get("/stories/{storyId}/chapters/{chapterId}", TEST_STORY_ID, TEST_CHAPTER_ID)).andExpect(status().isOk())
	            .andExpect(model().attributeExists("chapter")).andExpect(view().name("chapters/showChapter"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testInitNewChapterForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/chapters/new", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(view().name("chapters/editChapter"));
	      
	}
	  
		@WithMockUser(value = "spring")
		@Test
		void testProcessCreationChapterFormSuccess() throws Exception {
			mockMvc.perform(post("/stories/{storyId}/chapters/new", TEST_STORY_ID)
								.with(csrf())
								.param("id", "10")
								.param("index", "1")
								.param("title", "Prueba")
								.param("body", "Otra prueba más para probar la prueba que prueba la funcionalidad a prueba")
								.param("isPublished", "true")
								.param("story.id", "1"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("chapters/editChapter"));
		}

		@WithMockUser(value = "spring")
	    @Test
	void testProcessCreationChapterHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/new",TEST_STORY_ID)
							.with(csrf())
							.param("index", "")
							.param("title", "")
							.param("body", "")
							.param("isPublished", "")
							.param("story.id", ""))
							
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("chapter"))
				.andExpect(view().name("chapters/editChapter"));
		}

}
