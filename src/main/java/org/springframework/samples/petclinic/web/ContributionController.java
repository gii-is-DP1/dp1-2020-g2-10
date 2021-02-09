
package org.springframework.samples.petclinic.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.model.Contribution;
import org.springframework.samples.petclinic.model.ContributionType;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ContributionService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/stories/{storyId}")
public class ContributionController {
	
	private final ContributionService contributionService;
	private final StoryService storyService;
	private final AuthorService authorService;
	private static final String VISTA_EDICION_contribution= "contribution/editContribution";
	
	@Autowired
	public ContributionController(ContributionService contributionService, StoryService storyService,AuthorService authorService) {

		this.contributionService = contributionService;
		this.storyService = storyService;
		this.authorService = authorService;
	}
	
	@ModelAttribute("story")
	public Story findStory(@PathVariable("storyId") int storyId) {
		return this.storyService.findStoryById(storyId);
	}
	
	@GetMapping("/contribution/new")
	public String initAddContribution(ModelMap modelMap ,@PathVariable("storyId") int storyId) {
//		modelMap.put("buttonCreate", true);
		//Contribution contribution = this.contributionService.createContribution(story.getId());
		Contribution contribution = new Contribution();
		
		modelMap.put("contributionType", Arrays.asList(ContributionType.values()));
		modelMap.put("contribution", contribution);
		
		
		
		return VISTA_EDICION_contribution;
		
	}
	
	@PostMapping("/contribution/new")
	public String processNewContribution(@Valid Contribution contribution, BindingResult result, ModelMap modelMap, @PathVariable("storyId") int storyId) {
		
		Integer contribuidorId = contribution.getAuthor().getId();
		System.out.println("========================================================================");
		System.out.println("========================================================================");
		System.out.println(contribuidorId);
		
		// Si al validarlo, encontramos errores:
		
		if(result.hasErrors()) {
			
			modelMap.put("contribution", contribution);
			return VISTA_EDICION_contribution;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			contribution.setAuthor(authorService.findAuthorById(contribuidorId));
			Story story = storyService.findStory(storyId);
			contribution.setStory(story);
			try {
                contribution.setAuthor(authorService.findAuthorById(contribuidorId));
                contributionService.saveContribution(contribution);

            }catch (DataAccessException ex) {
                result.rejectValue("author.id","notExist" ,"You have to put an author that exists");
                return VISTA_EDICION_contribution;
            }
			
		
		}
		
		return "redirect:/stories/list";
		}
	
	
	

}