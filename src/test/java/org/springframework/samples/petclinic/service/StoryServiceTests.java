package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
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

	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldInsertStoryIntoDatabaseAndGenerateId() throws CannotPublishException {

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
		
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {

			this.storyService.saveStory(story);

		   });
		log.debug(exception.getMessage());
		assertEquals(exception.getMessage().contains("no puede estar vacío"), true);
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
		log.debug("========================================================================================================================");
		log.debug(exception.getMessage());
		assertEquals(exception.toString(), "java.lang.NullPointerException");
	}
	
	
	
	//Test HU-01: listado y mostrado de storys
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
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
