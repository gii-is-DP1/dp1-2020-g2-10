
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.samples.petclinic.service.exceptions.story.PublishedStoryUpdateExeption;
import org.springframework.samples.petclinic.service.exceptions.story.UnauthorizedStoryUpdateException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	    model.put("genres", Arrays.asList(Genre.values()));
		model.put("storyStatus", getAvailableStoryStatus());
		return VIEWS_STORIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/new")
	public String processCreationForm(Author author, @Valid Story story, BindingResult result, ModelMap model, RedirectAttributes redirectAttributes){		
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
			} catch (UnauthorizedStoryUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PublishedStoryUpdateExeption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			@RequestParam(value = "version", required=false) Integer version, RedirectAttributes redirectAttributes) throws UnauthorizedStoryUpdateException{		
		if (result.hasErrors()) {
			model.put("story", story);
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
			} catch (PublishedStoryUpdateExeption e) {
				redirectAttributes.addFlashAttribute("message", String.format("The story with storyId=%d is PUBLISHED, cannot and cannot be updated.", storyId, storyToUpdate.getAuthor().getUser().getUsername()));
				redirectAttributes.addFlashAttribute("messageType", "danger");
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
	
	@ExceptionHandler(UnauthorizedStoryUpdateException.class)
	public String accessDeniedExceptionHandler(HttpServletRequest request,  Exception ex, Model model) {
        request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("javax.servlet.error.status_code", 403);
        request.setAttribute("exeption", ex);
        
        
        model.addAttribute("messageType", "danger");
        model.addAttribute("message", ex.getMessage());
        return "errors/error403";
	}
	
	
}
