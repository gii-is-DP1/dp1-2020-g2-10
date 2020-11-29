package org.springframework.samples.petclinic.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public void saveStory(Story story){
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
		
		storyRepository.save(story);		
		
	}
	
	public Story createStory(){
		Story res = new Story();
		
		res.setAuthor(authorService.getPrincipal());
		res.setUpdatedDate(new Date());
		
		return res;
	}
	
	public Story findStoryById(int storyId) {
		return storyRepository.findById(storyId).orElseGet(null);
	}
	
	

}
