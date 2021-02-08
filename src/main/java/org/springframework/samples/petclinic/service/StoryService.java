package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoryService {
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ModeratorService moderatorService;
	
	public StoryService(StoryRepository storyRepository, AuthorService authorService,
			ModeratorService moderatorService) {
		super();
		this.storyRepository = storyRepository;
		this.authorService = authorService;
		this.moderatorService = moderatorService;
	}

	@Transactional(readOnly = true)	
	public Collection<Story> findStories() throws DataAccessException {
		return storyRepository.findStory(StoryStatus.PUBLISHED, StoryStatus.DRAFT);
	}	
	
	public Story findStoryById(int storyId) {
		return storyRepository.findById(storyId).orElseGet(null);
	}
	
	public Integer countReviewStories(StoryStatus review, Integer authorId) {
		return this.storyRepository.countReviewStories(review, authorId);
	}
	
	@Transactional(rollbackFor = CannotPublishException.class)
	public void saveStory(Story story) throws CannotPublishException{
		Story oldStory = null;
		
		if(story.getId()!=null) {
			oldStory = findStoryById(story.getId());
		}
		
		story.setAuthor(authorService.getPrincipal());
		story.setUpdatedDate(new Date());
		
		if(story.getStoryStatus().equals(StoryStatus.PUBLISHED) 
				&& (oldStory == null || oldStory.getStoryStatus().equals(StoryStatus.DRAFT)) ) {
			//TODO Asignar un moderador de forma mas "inteligente", distribuyendo las historias
			// entre los distintos moderadores de forma más o menos equitativa
			story.setModerator(moderatorService.findModeratorById(1));
		}
		//REGLA DE NEGOCIO 2
		Integer authorId = this.authorService.getPrincipal().getId();
		Integer reviewStories = countReviewStories(StoryStatus.REVIEW, authorId);
		if(reviewStories>=3) {
			throw new CannotPublishException();
		}else {
		storyRepository.save(story);		
		}
	}
	
	@Transactional(readOnly=true)
	public Story findStory (int storyId) throws DataAccessException{
		return storyRepository.findById(storyId).get();
	}
	
	public Story createStory(){
		Story res = new Story();
		
		
		res.setAuthor(authorService.getPrincipal());
		res.setUpdatedDate(new Date());
		
		return res;
	}
	
	public void deleteStory(int storyId) {
		Story story = findStoryById(storyId);
		Author principalAuthor = authorService.getPrincipal(); 
		
		assertNotNull(String.format("No story with ID=%d was found for deletion", storyId), story);
		assertTrue("Only the main author creator of the story can delete it.",
				story.getAuthor().equals(principalAuthor));
		assertTrue("Only stories as DRAFT can be deleted", story.getStoryStatus().equals(StoryStatus.DRAFT));

		
		storyRepository.delete(story);
	}
	
	
	public Collection<Story> getStoriesFromAuthorId(int authorId) {
		return storyRepository.getStoriesFromAuthorId(authorId);
	}

	
	@Transactional
	public void updateStory(int storyId) {
		
	
		storyRepository.setStoryStatus(StoryStatus.REVIEW, storyId);
		Story s = findStory(storyId);
		System.out.println("Estado historia actualizado" + s.getStoryStatus());
		
	}

}
