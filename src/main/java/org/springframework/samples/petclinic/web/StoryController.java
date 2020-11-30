package org.springframework.samples.petclinic.web;

import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Stories;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("authors/{authorId}")
public class StoryController {

	private static final String VIEWS_STORIES_CREATE_OR_UPDATE_FORM = "stories/createOrUpdateStoryForm";

	@Autowired
	private final StoryService storyService;

	public StoryController(StoryService storyService, AuthorService authorService) {
		super();
		this.storyService = storyService;
	}


	@GetMapping(value = "/stories/new")
	public String initCreationForm(Author author, ModelMap model) {

		Story story = storyService.createStory();
		model.put("story", story);		
		
//		Aqui la idea es meterle al modelo los generos.
//		Tambien se puede hacer como dijiste, poniendo los generos en el jsp
//		realmente no veo el problema, lo malo es que habria que escribirlos otra vez y que habria que indicar
//		el valor enumerado al que se refiere cada opcion.
//		
	    model.put("genres", Genre.values());
//		Lo mismo con storyStatus
		model.put("storyStatus", StoryStatus.values());
		return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
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

	@PostMapping(value = "/stories/new")
	public String processCreationForm(Author author, @Valid Story story, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("story", story);
			System.out.println(result);
			return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			storyService.saveStory(story);
            return "redirect:/welcome";

		}
	}
}
