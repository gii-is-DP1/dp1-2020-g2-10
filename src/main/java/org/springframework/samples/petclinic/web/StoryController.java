package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Stories;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class StoryController {

	private StoryService storyService;
	
	@Autowired
	public StoryController(StoryService storyService) {
		this.storyService = storyService;
	}

	
	@GetMapping(value = { "/stories" })
	public String showStoriesList(Map<String, Object> model) {
		Stories stories = new Stories();
		stories.getStoryList().addAll(this.storyService.findStories());
		model.put("stories", stories);
		return "stories/storyList";
	}
	
	@GetMapping(value = "/authors/*/stories/{storyId}")
	public String showStory(@PathVariable int storyId, Map<String, Object> model) {
		Story s= this.storyService.findStoryById(storyId);
		model.put("story", s);
		return "stories/storyList";
	}
	
	

}
