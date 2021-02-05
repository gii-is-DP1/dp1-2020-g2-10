package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReviewServiceTests {

	@Autowired
	protected StoryService storyService;

	@Autowired
	protected AuthorService authorService;
	
	@Autowired
	protected ReviewService reviewService;
	
	
	 
		@Test
		@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
		public void shouldCreateReviewAndGenerateId() {
		Author author1 = this.authorService.findAuthorById(1);

		Story s1 = this.storyService.findStoryById(1);
		System.out.println(s1);
			
		Review review = reviewService.createReview(s1);
		System.out.println("==============================================");
		System.out.println(review);
		
		// checks that id has been generated
		log.debug("===================================================================");
		log.debug(review.toString());
		assertThat(review).isNotNull();
	}
	
		
		@Test
		@WithMockUser(value = "author1", authorities = {
		        "author"
		    })
		@Transactional
		public void shouldInsertReviewIntoDatabaseAndGenerateId() {
//			Author author1 = this.authorService.findAuthorById(1);

			Review review = new Review();
			review.setTitle("La prueba positiva");
			review.setText("Buah que pasada, de verdad, la leería hasta que me muriese y un poquito más.");
			review.setRating(5);
			Story s1 = storyService.findStoryById(1);
			System.out.println(s1);
			review.setStory(s1);
			System.out.println("==============================================");
			System.out.println(review);
	        this.reviewService.saveReview(review);;
//			this.authorService.saveAuthor(author1);

//			author1 = this.authorService.findAuthorById(1);
			// checks that id has been generated
			log.debug("===================================================================");
			log.debug(review.getId().toString());
			assertThat(review.getId()).isNotNull();
		}
		
		
		@Test
		@WithMockUser(value = "author1", authorities = {
		        "author"
		    })
		@Transactional
		public void shouldNotInsertReviewIntoDatabaseBecauseTittleIsEmpty() {
			Author author1 = this.authorService.findAuthorById(1);
			List<Story> storiesA1 = storyService.getStoriesFromAuthorId(author1.getId()).stream().collect(Collectors.toList());

			Review review = new Review();
			review.setTitle("La prueba negativa");
			review.setText("Buah que pasada, de verdad, la leería hasta que me muriese y un poquito más porque tengo tendencias suicidas y es una basura.");
			
			Exception exception = assertThrows(ConstraintViolationException.class, () -> {

				this.reviewService.saveReview(review);

			   });
			log.debug("=====================================================================");
			log.debug(exception.getMessage());
			assertEquals(exception.getMessage().contains("no puede ser null"), true);
		}
		
		
		@Test
		@WithMockUser(value = "company1", authorities = {
		        "company"
		    })
		@Transactional
		public void shouldNotInsertReviewIntoDatabaseBecauseYouAreACompany() {
			Author author1 = this.authorService.findAuthorById(1);
			List<Story> storiesA1 = storyService.getStoriesFromAuthorId(author1.getId()).stream().collect(Collectors.toList());
			int found = storiesA1.size();

			Review review = new Review();
			review.setTitle("La prueba negativa");
			review.setText("Buah que pasada, de verdad, la leería hasta que me muriese y un poquito más porque tengo tendencias suicidas y es una basura.");
			review.setRating(0);
			
					
			Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {

				this.reviewService.saveReview(review);

			   });
			log.debug("=====================================================================");
			log.debug(exception.getMessage());
			assertEquals(exception.getMessage().contains("could not execute statement"), true);
 
		}
	
	

}