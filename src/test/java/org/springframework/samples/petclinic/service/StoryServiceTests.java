package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class StoryServiceTests {        
    @Autowired
	protected StoryService storyService;
        
    @Autowired
	protected AuthorService authorService;	

    
    
	//@Test
	@Transactional
	public void shouldInsertStoryIntoDatabaseAndGenerateId() {
		Author author1 = this.authorService.findAuthorById(1);
//		List<Story> storiesA1 = author1.getStories();
		int found = 2;

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcione");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(new Date());
		story.setUrlCover("/resources/images/author-pictures/author1.jpg");
		author1.addStory(story);
		assertThat(author1.getStories().size()).isEqualTo(found + 1);

        this.storyService.saveStory(story);
		this.authorService.saveAuthor(author1);

		author1 = this.authorService.findAuthorById(1);
		assertThat(author1.getStories().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(story.getId()).isNotNull();
	}
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean =
				new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldNotInsertStoryIntoDatabaseBecauseTittleIsEmpty() {
		Author author1 = this.authorService.findAuthorById(1);
		List<Story> storiesA1 = storyService.getStoriesFromAuthorId(author1.getId()).stream().collect(Collectors.toList());
		int found = storiesA1.size();

		Story story = new Story();
		story.setTitle("");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que no funcioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.DRAFT);
		
		Exception exception = assertThrows(ConstraintViolationException.class, () -> {

			this.storyService.saveStory(story);

		   });
//		System.out.println(exception.getMessage());
		assertEquals(exception.getMessage().contains("no puede estar vacío"), true);
	}
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldNotInsertStoryIntoDatabaseBecauseEverythingExceptTitleIsNull() {
		Author author1 = this.authorService.findAuthorById(1);
		List<Story> storiesA1 = storyService.getStoriesFromAuthorId(author1.getId()).stream().collect(Collectors.toList());
		int found = storiesA1.size();

		Story story = new Story();
		story.setTitle("Los demas campos vacios");
		story.setGenre(null);
		story.setDescription(null);
		story.setIsAdult(null);
		story.setStoryStatus(null);
				
		Exception exception = assertThrows(NullPointerException.class, () -> {

			this.storyService.saveStory(story);

		   });
//		System.out.println("========================================================================================================================");
//		System.out.println(exception);
		assertEquals(exception.toString(), "java.lang.NullPointerException");
//		assertEquals(exception.getMessage().contains("null"), true);
	}
	
	
	//@SuppressWarnings({ "deprecation", "static-access" })
	@Test
	void shouldFindStories()  {
		Collection<Story> stories = this.storyService.findStories();

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
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
//		Date d = sdf.parse("2020-10-12 15:00");
//		Date d = new Date(120, 9, 12, 15, 00);
		
//		d.parse("yyyy/MM/dd HH:mm");
//		d.parse("yyyy/MM/dd HH:mm");
		
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println(story.getUpdatedDate());
//		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
//		System.out.println("Hora y fecha: "+hourdateFormat.format("15:00:00 2020/10/12"));
//		assertThat(story.getUpdatedDate()).isEqualTo("15:00:00 2020/10/12");

	}
}
