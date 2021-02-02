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
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers= {ReportController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ReportControllerTests {
	
	private static final int TEST_REPORT_ID    = 1;
	private static final int TEST_CHAPTER_ID    = 1;
	private static final int TEST_STORY_ID = 1;

	@Autowired
	private ReportController reportController;

	@Autowired
	private AlexandriaErrorController alexandriaErrorController;
	
	@Autowired
	private AlexandriaControllerAdvice alexandriaControllerAdvice;
	
	@MockBean
	private ReportService reportService;
	
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
		 given(this.reportService.findReportById(TEST_REPORT_ID)).willReturn(new Report());
		 given(this.chapterService.findChapterById(TEST_CHAPTER_ID)).willReturn(new Chapter());
		 
	}
	
	
	@WithMockUser(value = "spring")
	   @Test
		void testInitNewReportForm() throws Exception {
			mockMvc.perform(get("/stories/{storyId}/chapters/{chapterId}/reports/new", TEST_STORY_ID, TEST_CHAPTER_ID)).andExpect(status().isOk())
					.andExpect(view().name("reports/editReport"));
		}
	      
	    
	    
		@WithMockUser(value = "spring")
		@Test
		void testProcessCreationReportFormSuccess() throws Exception {
			mockMvc.perform(post("/stories/{storyId}/chapters/{chapterId}/reports/new", TEST_STORY_ID, TEST_CHAPTER_ID)
								.with(csrf())
								.param("id", "1")
								.param("reportType", "1")
								.param("reportStatus", "2")
								.param("date", "2022/04/26 13:05")
								.param("text", "No me gusta nada esta obra la verdad y por eso emito este reporte")
								.param("chapter.id", "1"))
			.andExpect(status().is2xxSuccessful()).andExpect(view().name("reports/editReport"));
		}

		
		@WithMockUser(value = "spring")
	    @Test
	void testProcessCreationReportHasErrors() throws Exception {
		mockMvc.perform(post("/stories/{storyId}/chapters/{chapterId}/reports/new",TEST_STORY_ID, TEST_CHAPTER_ID)
							.with(csrf())
							.param("reportType", "")
							.param("reportStatus", "")
							.param("date", "")
							.param("text", "")
							.param("chapter.id", ""))
							
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("report"))
				.andExpect(view().name("reports/editReport"));
		}
		
}

