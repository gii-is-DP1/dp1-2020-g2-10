package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class StoryService {

	private StoryRepository storyRepository;

	public StoryService(StoryRepository storyRepository) {
		super();
		this.storyRepository = storyRepository;
	}
	
	@Transactional
	public void saveStory(Story story) throws DataAccessException {
		storyRepository.save(story);
	}
	
	@Transactional(readOnly = true)
	public Story findStoryById(int id) throws DataAccessException {
		return 	storyRepository.findStoryById(id);
	}
}