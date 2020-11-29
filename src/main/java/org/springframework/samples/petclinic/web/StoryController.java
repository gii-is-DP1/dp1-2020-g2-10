package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors/{authorId}")
public class StoryController {

	private static final String VIEWS_STORIES_CREATE_OR_UPDATE_FORM = "stories/createOrUpdateStoryForm";

	private final StoryService storyService;
    private final AuthorService authorService;

	@Autowired
	public StoryController(StoryService storyService, AuthorService authorService) {
		this.storyService = storyService;
                this.authorService = authorService;
	}
	
	@GetMapping(value = "/story/{storyId}/edit")
	public String initUpdateForm(@PathVariable("storyId") int storyId, ModelMap model) {
		Story story = this.storyService.findStoryById(storyId);
		model.put("story", story);
		return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
	}

  
    @PostMapping(value = "/story/{storyId}/edit")
	public String processUpdateForm(@Valid Story story, BindingResult result, Author author,@PathVariable("storyId") int storyId, ModelMap model) {
		if (result.hasErrors()) {
			model.put("story", story);
			return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			story.setId(storyId);
			this.storyService.saveStory(story);
            //Story storyToUpdate=this.storyService.findStoryById(storyId);
			//BeanUtils.copyProperties(story, storyToUpdate, "id","author");                                                                                  
            return "redirect:/author/{authorId}";
		}
	}
}
	
	

		