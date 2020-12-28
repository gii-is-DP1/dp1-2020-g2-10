package org.springframework.samples.petclinic.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.repository.ReviewRepository;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private AuthorService authorService;
	
	
	@Transactional
	public void saveReview(Review review){
		
		review.setAuthor(authorService.getPrincipal());
		review.setPublicationDate(new Date());
		reviewRepository.save(review);		
		
	}
	
	public Review createReview(Story story){
		Review res = new Review();
		
		res.setStory(story);
		res.setAuthor(authorService.getPrincipal());
		res.setPublicationDate(new Date());
		
		return res;
	}
	
}
