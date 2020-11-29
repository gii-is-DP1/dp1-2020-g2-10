package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoryService {
	
	private StoryRepository storyRepository;
	
	@Autowired
	public StoryService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}	

	@Transactional(readOnly = true)	
	public Collection<Story> findStories() throws DataAccessException {
		return storyRepository.findAll();
	}	
	
	@Transactional(readOnly = true)
	public Story findStoryById(int id) throws DataAccessException {
		return storyRepository.findStoryById(id);
	}
	
	@Transactional
	public void saveStory(Story story) throws DataAccessException {
		
		storyRepository.save(story);		
		
	}		
	
	public Story findStoryById(int storyId){
		return storyRepository.findStoryById(storyId);
	}
	

}
