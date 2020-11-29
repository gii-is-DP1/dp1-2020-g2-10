package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StoryServiceTests {

	@Autowired
	protected StoryService storyService;	

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
		//SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//Date d = sdf.parse("2020-10-12 15:00");
//		Date d = new Date(120, 9, 12, 15, 00);
		
//		d.parse("yyyy/MM/dd HH:mm");
//		d.parse("yyyy/MM/dd HH:mm");
		
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println(story.getUpdatedDate());
//		assertThat(story.getUpdatedDate()).isEqualTo("2020-10-12 15:00");
		
		
	

	}
	
}
