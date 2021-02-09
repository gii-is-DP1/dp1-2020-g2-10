package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Review;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ReviewService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/stories/{storyId}")
@Slf4j
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	@Autowired
	StoryService storyService;
	@Autowired
	AuthorService authorService;
	
	
	private static final String VISTA_EDICION_review= "review/editReview";
	
	@Autowired
	public ReviewController(ReviewService reviewService, StoryService storyService,AuthorService authorService) {

		this.reviewService = reviewService;
		this.storyService = storyService;
		this.authorService = authorService;
	}
	
//	@ModelAttribute("story")
//	public Story findStory(@PathVariable("storyId") int storyId) {
//		return this.storyService.findStoryById(storyId);
//	}
	
	@GetMapping("/reviews/new")
	public String initAddReview(@PathVariable("storyId") int storyId, ModelMap modelMap) {
//		modelMap.put("buttonCreate", true);
		Story story = this.storyService.findStoryById(storyId);
		Review review = this.reviewService.createReview(story);
		
		
		modelMap.put("review", review);
		modelMap.put("story", story);
		
		return VISTA_EDICION_review;
	}
	
	@PostMapping("/reviews/new")
	public String processNewReview(@PathVariable("storyId") int storyId, @Valid Review review,  BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) {
		
		modelMap.put("buttonCreate", true);
		
		Story story = this.storyService.findStoryById(storyId);
		// Si al validarlo, encontramos errores:
		System.out.println("========================");
		System.out.println(result);
		System.out.println(result.hasErrors());
		if(result.hasErrors() || review.getRating() == null || review.getRating() < 0 || review.getRating() > 5) {
			modelMap.put("review", review);
			log.debug("====================================================================================");
			log.debug(result.toString());
			redirectAttributes.addFlashAttribute("message", String.format("The review with storyId=%d was not created.", story.getId()));
			redirectAttributes.addFlashAttribute("messageType", "warning");
			return VISTA_EDICION_review;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			review.setStory(story);
			reviewService.saveReview(review);
			modelMap.addAttribute("messageSuccess", "¡La review se ha añadido con éxito!");
			redirectAttributes.addFlashAttribute("message", String.format("The review for %s was created.", story.getTitle()));
			redirectAttributes.addFlashAttribute("messageType", "success");
			return "redirect:/stories/{storyId}/show";
		}
		
		}
	
	
	

}
