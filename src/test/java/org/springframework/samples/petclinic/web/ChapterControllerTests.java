package org.springframework.samples.petclinic.web;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(controllers= {ChapterController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ChapterControllerTests {
	
	private static final int TEST_CHAPTER_ID = 1;
	private static final int TEST_STORY_ID = 1;
	private static final int TEST_AUTHOR_ID = 1;

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
	private AuthorService authorService;
	
  @MockBean
  private UserService userService;	
  
  @Autowired
	private MockMvc mockMvc;

    @BeforeEach
    void setup() {

        Story s = new Story();
            s.setId(TEST_STORY_ID);
            s.setStoryStatus(StoryStatus.PUBLISHED);

         Chapter c = new Chapter();
         c.setId(TEST_CHAPTER_ID);
         c.setIndex(1);
         c.setTitle("Prueba");
         c.setBody("Otra prueba más para probar la prueba que prueba la funcionalidad a prueba");
         c.setIsPublished(false);
         c.setStory(s);


         given(this.chapterService.findChapterById(TEST_CHAPTER_ID)).willReturn(c);
         given(this.storyService.findStory(TEST_STORY_ID)).willReturn(s);
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
	void testInitUpdateForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/chapters/{chapterId}/edit", TEST_STORY_ID, TEST_CHAPTER_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("buttonCreate"))
				.andExpect(model().attributeExists("chapter")).andExpect(model().attributeExists("storyId"))
				.andExpect(model().attributeExists("chapterId"))
				.andExpect(view().name("chapters/editChapter"));
	}
	
	// ------- H6+E1 - Escenario positivo ---------
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccessE1() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/{chapterId}/edit", TEST_STORY_ID, TEST_CHAPTER_ID)
							.with(csrf())
							.param("index", "2")
							.param("title", "Titulo Actualizado Success")
							.param("body", "Esto es un capítulo que se actualiza bien")
							.param("isPublished", "false")
							.param("story.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/{storyId}/chapters/{chapterId}"));
	}
	
	// ------- H6+E2 - Escenario positivo ---------
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateFormSuccessE2() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/{chapterId}/edit", TEST_STORY_ID, TEST_CHAPTER_ID)
							.with(csrf())
							.param("index", "2")
							.param("title", "Titulo Actualizado Success")
							.param("body", "Esto es un capítulo que se actualiza bien")
							.param("isPublished", "true")
							.param("story.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/stories/{storyId}/chapters/{chapterId}"));
	}
	
	// ------- H6-E1 - Escenario negativo ---------
	
	  @WithMockUser(value = "spring")
	  @Test
		void testProcessUpdateFormHasErrorsE1() throws Exception {
			mockMvc.perform(post("/stories/{storyId}/chapters/{chapterId}/edit", TEST_STORY_ID, TEST_CHAPTER_ID)
								.with(csrf())
								.param("index", "2")
								.param("title", "")
								.param("body", "")
								.param("isPublished", "true")
								.param("story.id", "1"))
					.andExpect(model().attributeHasErrors("chapter"))
					.andExpect(model().attributeHasFieldErrors("chapter", "title","body"))
					.andExpect(view().name("chapters/editChapter"));
		}
	
	@WithMockUser(value = "spring")
  @Test
	void testInitNewChapterForm() throws Exception {
		mockMvc.perform(get("/stories/{storyId}/chapters/new", TEST_STORY_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("chapter")).andExpect(view().name("chapters/editChapter"));
	      
	}
	  
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationChapterFormSuccess() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/new", TEST_STORY_ID)
							.with(csrf())
							.param("index", "1")
							.param("title", "Prueba")
							.param("body", "Prueba que funciona")
							.param("isPublished", "false")
							.param("story.id", "1"))
		
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/stories/{storyId}/chapters"));
	}

	
 @WithMockUser(value = "spring")
  @Test
	void testProcessCreationChapterHasTrueErrorPublished() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/new",TEST_STORY_ID)
							.with(csrf())
							.param("index", "2")
							.param("title", "")
							.param("body", "")
							.param("isPublished", "true")
							.param("story.id", "1"))
				.andExpect(model().attributeHasErrors("chapter"))
				.andExpect(model().attributeExists("errorPublished"))
				.andExpect(view().name("chapters/editChapter"));
		}
		
		

	
	

}
