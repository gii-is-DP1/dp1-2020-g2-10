
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/stories")
public class StoryController {

	private static final String VIEWS_STORIES_CREATE_OR_UPDATE_FORM = "stories/createOrUpdateStoryForm";
	
	private static final String VIEWS_STORIES_LIST= "stories/storiesList";
	
	private static final String VIEWS_STORIES_SHOW = "stories/showStory";

	
	@Autowired
	private final StoryService storyService;

	public StoryController(StoryService storyService, AuthorService authorService) {
		super();
		this.storyService = storyService;
	}

	@GetMapping(value = { "/list" })
	public String showStoriesList(Map<String, Object> model) {
		Collection<Story> stories = this.storyService.findStories();
		model.put("stories", stories);
		return VIEWS_STORIES_LIST;
	}
	
	@GetMapping(value = "/{storyId}/show")
	public String showStory(@PathVariable int storyId, Map<String, Object> model) {
		Story s= this.storyService.findStoryById(storyId);
		model.put("story", s);
		return VIEWS_STORIES_SHOW;
	}
	
	@GetMapping(value = "/new")
	public String initCreationForm(ModelMap model) {

		Story story = storyService.createStory();
		model.put("story", story);		
		
//		Aqui la idea es meterle al modelo los generos.
//		Tambien se puede hacer como dijiste, poniendo los generos en el jsp
//		realmente no veo el problema, lo malo es que habria que escribirlos otra vez y que habria que indicar
//		el valor enumerado al que se refiere cada opcion.
//		
	    model.put("genres", Arrays.asList(Genre.values()));
//		Lo mismo con storyStatus
		model.put("storyStatus", getAvailableStoryStatus());
		return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(Author author, @Valid Story story, BindingResult result, ModelMap model) throws CannotPublishException {		
		if (result.hasErrors()) {
			model.put("story", story);
			System.out.println(result);
			return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			try { //Intentamos capturar la excepcion de la Regla de Negocio 2
			storyService.saveStory(story);
			}
			catch (CannotPublishException ex){
				result.rejectValue("storyStatus","banned" ,"You have 3 stories in review");
				return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
			}	return "redirect:/stories/list";
		}
	}
	
	@GetMapping(value = "/{storyId}/edit")
	public String editCreationForm(@PathVariable int storyId, ModelMap model) {

		Story story = storyService.findStory(storyId);
		model.put("story", story);				
	    model.put("genres", Arrays.asList(Genre.values()));
//		Lo mismo con storyStatus
		model.put("storyStatus", getAvailableStoryStatus());
		return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/{storyId}/edit")
	public String processEditCreationForm(@PathVariable int storyId, @Valid Story story, BindingResult result, ModelMap model,
			@RequestParam(value = "version", required=false) Integer version) throws CannotPublishException {		
		if (result.hasErrors()) {
			model.put("story", story);
			System.out.println(result);
			return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			Story storyToUpdate = storyService.findStoryById(story.getId());
			if(storyToUpdate.getVersion()!=version) {
				model.put("message","Concurrent modification of story! Try again!");
				model.put("messageType","warning");
				return editCreationForm(story.getId(),model);
				}
			try { //Intentamos capturar la excepcion de la Regla de Negocio 2
				storyService.saveStory(story);
			} catch (CannotPublishException ex) {
				result.rejectValue("storyStatus","banned" ,"You have 3 stories in review");
				return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
			}return "redirect:/stories/list";

		}
	}
	
	@GetMapping(value = { "/{storyId}/delete"})
	public String deleteStory(@PathVariable int storyId, RedirectAttributes redirectAttributes) {
		storyService.deleteStory(storyId);
		redirectAttributes.addFlashAttribute("message", String.format("The story with storyId=%d was deleted.", storyId));
		redirectAttributes.addFlashAttribute("messageType", "success");
		return "redirect:/stories/list";
	}
	
	private Collection<StoryStatus> getAvailableStoryStatus(){
		Collection<StoryStatus> res = new HashSet<StoryStatus>();
		res.add(StoryStatus.DRAFT);
		res.add(StoryStatus.PUBLISHED);
		
		return res;
	}
	
	
}
