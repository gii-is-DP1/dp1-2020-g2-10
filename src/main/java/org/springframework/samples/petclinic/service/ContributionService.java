package org.springframework.samples.petclinic.service;




import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Contribution;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.repository.ContributionRepository;
import org.springframework.samples.petclinic.repository.ReviewRepository;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContributionService {
	
	@Autowired
	private ContributionRepository contributionRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private StoryService storyService;
	
	
	@Transactional
	public void saveContribution(Contribution contribution){
		
		//contribution.setAuthor(authorService.findAuthorById(contribution.getAuthor().getId()));
		contributionRepository.save(contribution);		
		
	}
	
	
	
	public Contribution findContributionById(int contributionId) {
		return contributionRepository.findById(contributionId).orElseGet(null);
	}
	
}