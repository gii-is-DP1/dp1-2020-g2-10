package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stories/{storyId}")
public class ReviewController {
	
	private final ReviewService reviewService;
	private final StoryService storyService;
	private final AuthorService authorService;
	private static final String VISTA_EDICION_review= "review/editReview";
	
	@Autowired
	public ReviewController(ReviewService reviewService, StoryService storyService,AuthorService authorService) {

		this.reviewService = reviewService;
		this.storyService = storyService;
		this.authorService = authorService;
	}
	
	@ModelAttribute("story")
	public Story findStory(@PathVariable("storyId") int storyId) {
		return this.storyService.findStoryById(storyId);
	}
	
	@GetMapping("/reviews/new")
	public String initAddReview(Story story, ModelMap modelMap) {
//		modelMap.put("buttonCreate", true);
		Review review = this.reviewService.createReview(story);
		modelMap.put("review", review);
		modelMap.put("story", story);
		
		return VISTA_EDICION_review;
		
	}
	
	@PostMapping("/reviews/new")
	public String processNewReview(Story story, @Valid Review review, BindingResult result, ModelMap modelMap) {
		
		modelMap.put("buttonCreate", true);
		
		// Si al validarlo, encontramos errores:
		
		if(result.hasErrors()) {
			
			modelMap.put("review", review);
			System.out.println("====================================================================================");
			System.out.println(result);
			return VISTA_EDICION_review;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			review.setStory(story);
			reviewService.saveReview(review);
			modelMap.addAttribute("messageSuccess", "¡La review se ha añadido con éxito!");
		
		}
		
		return "redirect:/stories/{storyId}/show";
		}
	
	
	

}
