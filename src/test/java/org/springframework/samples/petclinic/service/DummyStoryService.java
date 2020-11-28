package org.springframework.samples.petclinic.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.repository.AuthorRepository;

public class DummyStoryService {

	@Autowired
	protected StoryService storyService;
        
    @Autowired
	protected AuthorService authorService;	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		Author author1 = AuthorService.findAuthorById(1);
		int found = author1.getStories().size();

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcione");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(Date.valueOf(LocalDate.of(03, 05, 2020)));
		story.setUrlCover("/resources/images/author-pictures/author1.jpg");
		author1.addStory(story);
		
	}

}
