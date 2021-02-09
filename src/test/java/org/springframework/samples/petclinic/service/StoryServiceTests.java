package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.samples.petclinic.service.exceptions.story.PublishedStoryUpdateExeption;
import org.springframework.samples.petclinic.service.exceptions.story.UnauthorizedStoryUpdateException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class StoryServiceTests {        
    @Autowired
	protected StoryService storyService;
        
    @Autowired
	protected AuthorService authorService;
    
    /* Loading the EntityManager is necessary to detach entities fetched from database
     * entityManager.detach(storyToUpdate);
     * 
     * If not, every entity.setProperty() will be immediately persisted, even before the save()
     * more information at: https://stackoverflow.com/questions/26795436/spring-jparepository-detach-and-attach-entity/26812963#answer-65586348
     * also: https://www.objectdb.com/java/jpa/persistence/detach
     * 
     * - Carlos (carcabcol) */
    @PersistenceContext
    private EntityManager entityManager;
    
    private final Integer AUTHOR1_PUBLISHED_STORY_ID = 1;
    private final Integer AUTHOR1_DRAFT_STORY_ID = 2;
    
    @Test
	@WithMockUser(value = "author1", authorities = {"author"})
    public void shouldInitializeStory() {
    	// 1. Arrange
    	Author expectedAuthor = authorService
    			.findByUserUsername("author1").orElseGet(null);
    	
    	// 2. Act
    	Story createdStory = storyService.createStory();
    	
    	// 3. Assert
    	assertThat(createdStory.getAuthor()).isEqualTo(expectedAuthor);
    	assertThat(createdStory.getUpdatedDate()).isBefore(new Date());	
    }
    
    	@Test
    	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
    	@Transactional
	public void shouldInsertStoryIntoDatabaseAndGenerateId() throws CannotPublishException, UnauthorizedStoryUpdateException, PublishedStoryUpdateExeption {

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(new Date());

        this.storyService.saveStory(story);

		// checks that id has been generated
		assertThat(story.getId()).isNotNull();
	}
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldNotInsertStoryIntoDatabaseBecauseTittleIsEmpty() {

		Story story = new Story();
		story.setTitle("");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que no funcioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.DRAFT);
		
		assertThrows(ConstraintViolationException.class, () -> {

			this.storyService.saveStory(story);

		   });
	}
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldNotInsertStoryIntoDatabaseBecauseEverythingExceptTitleIsNull() {

		Story story = new Story();
		story.setTitle("Los demas campos vacios");
		story.setGenre(null);
		story.setDescription(null);
		story.setIsAdult(null);
		story.setStoryStatus(null);
				
		Exception exception = assertThrows(NullPointerException.class, () -> {

			this.storyService.saveStory(story);

		   });
	}
	
	/* === H3 Actualizar historia (Carlos - carcabcol) ========================== */
	
	@Nested
	@DisplayName("H3: Update story")
	@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class)) // Each test will rollback transactions
	class StoryServiceTestsH3{
	
		// H3+E1 Actualizar historia
		@Test
		@WithMockUser(value = "author1", authorities = {"author"})
		@DisplayName("H3+E1: Should update DRAFT story")
		public void shouldUpdateDraftStoryTitle() throws CannotPublishException, UnauthorizedStoryUpdateException, PublishedStoryUpdateExeption {
			// 1. Arrange
			Story storyToUpdate = storyService.findStoryById(AUTHOR1_DRAFT_STORY_ID);
			entityManager.detach(storyToUpdate);
			
			Date previousStoryUpdate = storyToUpdate.getUpdatedDate();
			
			// 2. Act
			String updateTitle = "Updated title";
			storyToUpdate.setTitle(updateTitle);
			
			storyService.saveStory(storyToUpdate);
			
			// 3. Assert
			Story storyAfterUpdate = storyService.findStoryById(AUTHOR1_DRAFT_STORY_ID);
			assertThat(storyToUpdate.getTitle()).isEqualTo(updateTitle);
			assertThat(storyToUpdate.getUpdatedDate()).isAfter(previousStoryUpdate);
			assertThat(storyToUpdate.getUpdatedDate()).isBefore(new Date());
		}
		
		// H3+E2 Publicar historia inicialmente en borrador
		@Test
		@WithMockUser(value = "author1", authorities = {"author"})
		@DisplayName("H3+E2: Should publish DRAFT story")
		public void shouldPublishDraftStory() throws CannotPublishException, UnauthorizedStoryUpdateException, PublishedStoryUpdateExeption {
			// 1. Arrange
			Story storyToUpdate = storyService.findStoryById(AUTHOR1_DRAFT_STORY_ID);
			assertThat(storyToUpdate.getStoryStatus()).isEqualTo(StoryStatus.DRAFT);
			Date previousStoryUpdate = storyToUpdate.getUpdatedDate();
			
			entityManager.detach(storyToUpdate);
			
			// 2. Act
			storyToUpdate.setStoryStatus(StoryStatus.PUBLISHED);
			storyService.saveStory(storyToUpdate);
			
			// 3. Assert
			assertThat(storyToUpdate.getStoryStatus()).isEqualTo(StoryStatus.PUBLISHED);
			assertThat(storyToUpdate.getUpdatedDate()).isAfter(previousStoryUpdate);
			assertThat(storyToUpdate.getUpdatedDate()).isBefore(new Date());
		}
		
		// H3-E1 No actualizar un borrador de historia de otro autor
		@Test
		@WithMockUser(value = "author2", authorities = {"author"})
		@DisplayName("H3-E1: Forbid update DRAFT story by other author")
		public void shouldNotUpdateDraftStory() throws CannotPublishException {
			// 1. Arrange
			Story storyToUpdate = storyService.findStoryById(AUTHOR1_DRAFT_STORY_ID);
			String previousStoryTitle = storyToUpdate.getTitle();
			assertThat(storyToUpdate.getStoryStatus()).isEqualTo(StoryStatus.DRAFT);
			
			entityManager.detach(storyToUpdate);
			
			// 2. Act
			storyToUpdate.setTitle("This update should not take place");
			Assertions.assertThatThrownBy(() -> {
				
				storyService.saveStory(storyToUpdate);})
			//3. Assert
			.isInstanceOf(UnauthorizedStoryUpdateException.class);
			Story storyAfterUpdate = storyService.findStoryById(AUTHOR1_DRAFT_STORY_ID);
			assertThat(storyAfterUpdate.getTitle()).isEqualTo(previousStoryTitle);
		}
		
		// H3-E2 No actualizar una historia ya publicada
		@Test
		@WithMockUser(value = "author1", authorities = {"author"})
		@DisplayName("H3-E2: Forbid update of a PUBLISHED story")
		public void shouldNotUpdatePublishedStory() throws CannotPublishException {
			// 1. Arrange
			Story storyToUpdate = storyService.findStoryById(AUTHOR1_PUBLISHED_STORY_ID);
			String previousStoryTitle = storyToUpdate.getTitle();
			assertThat(storyToUpdate.getStoryStatus()).isEqualTo(StoryStatus.PUBLISHED);
			
			entityManager.detach(storyToUpdate);
			
			// 2. Act
			storyToUpdate.setTitle("This update should not take place");
			Assertions.assertThatThrownBy(() -> {
				
				storyService.saveStory(storyToUpdate);})
			//3. Assert
			.isInstanceOf(PublishedStoryUpdateExeption.class);
			Story storyAfterUpdate = storyService.findStoryById(AUTHOR1_PUBLISHED_STORY_ID);
			assertThat(storyAfterUpdate.getTitle()).isEqualTo(previousStoryTitle);
		}
	}
	
	//Test HU-01: listado y mostrado de storys
	@Test
	void shouldFindStories()  {
		Collection<Story> stories = this.storyService.findStories();
		
		Timestamp updatedDate = new Timestamp(120, 9, 12, 15, 00, 0, 0); 


		Story story = EntityUtils.getById(stories, Story.class, 1);
		
		assertThat(story.getTitle()).isEqualTo("Lorem ipsum");
		assertThat(story.getDescription()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum "
				+ "at blandit dolor, at laoreet nulla. Donec nibh nisi.");
		assertThat(story.getDedication()).isEqualTo("Dicata latine loquentium");
		Genre g= Genre.NOVEL;
		assertThat(story.getGenre()).isEqualTo(g);
		StoryStatus s= StoryStatus.PUBLISHED;
		assertThat(story.getStoryStatus()).isEqualTo(s);
		Boolean b= Boolean.FALSE;
		assertThat(story.getIsAdult()).isEqualTo(b);
		assertThat(story.getUpdatedDate()).isEqualTo(updatedDate);
		

		
		// No hay test negativo al ser un listado

	}
}
