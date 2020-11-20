package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.repository.StoryRepository;

public class StoryService {
	
	private StoryRepository storyRepository;
	
	@Autowired
	public StoryService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}	
	
	public Story findStoryById(int storyId){
		return storyRepository.findStoryById(storyId);
	}

}
