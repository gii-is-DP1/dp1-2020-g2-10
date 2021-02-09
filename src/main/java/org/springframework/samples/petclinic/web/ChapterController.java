package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("stories/{storyId}")
public class ChapterController {
	
	private final ChapterService chapterService;
	private final StoryService storyService;
	private final AuthorService authorService;
	private static final String VISTA_EDICION_chapter= "chapters/editChapter";
	private static final String VIEW_LIST_CHAPTERS="chapters/listChapters";
	private static final String VIEW_SHOW_CHAPTER="chapters/showChapter";
	
	@Autowired
	public ChapterController(ChapterService chapterService, StoryService storyService, AuthorService authorService) {

		this.chapterService = chapterService;
		this.storyService = storyService;
		this.authorService = authorService;
	}
	
	@InitBinder
 	public void setAllowedFields(WebDataBinder dataBinder) {
 		dataBinder.setDisallowedFields("id");
 	}
			
	// HU-16: Listar y mostrar capítulos.
	
	@GetMapping(value = { "/chapters" })
	public String listChaptersOfStory(@PathVariable("storyId") int storyId, ModelMap modelMap) {
		Iterable<Chapter> chapters = this.chapterService.findChapterByStoryId(storyId);
		modelMap.put("chapters", chapters);
		return VIEW_LIST_CHAPTERS;
	}
	
	@GetMapping(value = { "/chapters/{chapterId}" })
	public String showChapter(@PathVariable("chapterId") int chapterId, ModelMap modelMap) {
		Chapter chapter = this.chapterService.findChapterById(chapterId);
		modelMap.put("chapter", chapter);
		return VIEW_SHOW_CHAPTER;
	}
		
	
	// HU-05: Añadir un capítulo a una historia.
	
	// En el primer método get, mostramos el formulario de edición del nuevo capítulo:
	@GetMapping("/chapters/new")
	public String initAddChapter(@PathVariable("storyId") int storyId, ModelMap modelMap) {
		modelMap.addAttribute("buttonCreate", true);
		Chapter chapter = new Chapter();
		chapter.setIsPublished(false);
		modelMap.addAttribute("chapter", chapter);
		modelMap.addAttribute("storyId", storyId);
		
		return VISTA_EDICION_chapter;
		
	}
	
	// En este último post procesamos el capítulo recién creado. Lo validamos y se añade al listado de capítulos, si es correcto:
	@PostMapping("/chapters/new")
	public String processNewChapter(@Valid Chapter chapter, BindingResult result, @PathVariable("storyId") int storyId,  ModelMap modelMap) {
	//throws CannotPublishException{

		
		modelMap.addAttribute("buttonCreate", true);
		Story story = this.storyService.findStory(storyId);
		
		//No puedes hacer público un capítulo si las historia no esta publicada
		
			ObjectError error1 = new ObjectError("isPublished", "No puedes publicar un capítulo si tu historia aún no lo está.");
			if(!(story.getStoryStatus().equals(StoryStatus.PUBLISHED)) && chapter.getIsPublished()) {
				
				result.addError(error1);
			}
		
		
		//----
		
		// Si al validarlo, encontramos errores:
		
		if (result.hasErrors()) {
			modelMap.addAttribute("chapter", chapter);
			
			if(chapter.getIsPublished().equals(true) && result.getAllErrors().contains(error1)) {
				modelMap.addAttribute("errorPublished", true);
			}else {
				modelMap.addAttribute("errorPublished", false);
			}
			return VISTA_EDICION_chapter;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			chapter.setStory(story);
			try {
			this.chapterService.saveChapter(chapter);
			} catch(DataIntegrityViolationException ex) {
				result.rejectValue("index","coincide" ,"You must define unique index");
				return  VISTA_EDICION_chapter;
			}
			modelMap.addAttribute("messageSuccess", "¡El capítulo se ha añadido con éxito!");
			return "redirect:/stories/{storyId}/chapters";
		
		}
		
		
		
		}
	
	
	//HU-06 Edición de un capitulo
			@GetMapping(value = "/chapters/{chapterId}/edit")
			public String initUpdateChapterForm(@PathVariable("chapterId") int chapterId, 
					@PathVariable("storyId") int storyId, ModelMap model) {
				model.addAttribute("buttonCreate", false); //si esta a false el boton de create no aparecera
				Chapter chapter = this.chapterService.findChapterById(chapterId);
				model.addAttribute("chapter", chapter);
				model.addAttribute("storyId", storyId);
				model.addAttribute("chapterId", chapterId);
				return VISTA_EDICION_chapter;
			}
			
			@PostMapping(value = "/chapters/{chapterId}/edit")
			public String processUpdateChapterForm(@Valid Chapter chapter, BindingResult result,
					@PathVariable("chapterId") int chapterId, 
					@PathVariable("storyId") int storyId,
					@RequestParam(value = "version", required=false) Integer version,
					ModelMap model) throws DataAccessException{
				Story story = this.storyService.findStory(storyId);
				Chapter chapterToUpdate = this.chapterService.findChapterById(chapterId);
				//VERSIONADO
				if(chapterToUpdate.getVersion() != version) {
					model.put("message", "Concurrent modification of chapter! Try again!");
					return initUpdateChapterForm(chapterId, storyId, model);
				}
				//No puedes hacer público un capítulo si las historia no esta publicada
				ObjectError error1 = new ObjectError("isPublished", "No puedes publicar un capítulo si tu historia aún no lo está.");
				if(!(story.getStoryStatus().equals(StoryStatus.PUBLISHED)) && chapter.getIsPublished()) {
					result.addError(error1);
				}
				if (result.hasErrors()) {
					if(chapter.getIsPublished().equals(true) && result.getAllErrors().contains(error1)) {
						model.addAttribute("errorPublished", true);
					}else {
						model.addAttribute("errorPublished", false);
					}
					return VISTA_EDICION_chapter;
				}
				else {
					chapter.setId(chapterId);
					chapter.setStory(story);
					try {
						this.chapterService.saveChapter(chapter);
						} catch(DataIntegrityViolationException ex) {
							result.rejectValue("index","coincide" ,"You must define unique index");
							return  VISTA_EDICION_chapter;
						}
					
					return "redirect:/stories/{storyId}/chapters/{chapterId}";
				}
			}
			
	// HU-07: Borrar un capítulo mientras esté en borrador
			@GetMapping(value = "/chapters/{chapterId}/delete")
			public String deleteChapter(@PathVariable("chapterId") int chapterId, ModelMap model) {
				try {
					System.out.println("ChapterDeletion is being called!");
					chapterService.deleteChapter(chapterId);
					model.addAttribute("messageSuccess", "¡El capítulo se ha eliminado con éxito!");
				}catch (AssertionError error){
		            // Output expected AssertionErrors.
		            error.printStackTrace();
		            
				}
				return "redirect:/stories/{storyId}/chapters";
			}
			
	
}

