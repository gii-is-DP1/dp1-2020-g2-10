package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.StoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public ChapterController(ChapterService chapterService, StoryService storyService,AuthorService authorService) {

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
	public String initAddChapter(ModelMap modelMap) {
		modelMap.put("buttonCreate", true);
		Chapter chapter = new Chapter();
		modelMap.put("chapter", chapter);
		
		
		return VISTA_EDICION_chapter;
		
	}
	
	// En este último post procesamos el capítulo recién creado. Lo validamos y se añade al listado de capítulos, si es correcto:
	@PostMapping("/chapters/new")
	public String processNewChapter(@Valid Chapter chapter, BindingResult result, @PathVariable("storyId") int storyId,  ModelMap modelMap) {
		
		modelMap.put("buttonCreate", true);

		Story story = this.storyService.findStory(storyId);
		//No puedes hacer público un capítulo si las historia no esta publicada
		if(!(story.getStoryStatus().equals(StoryStatus.PUBLISHED)) && chapter.getIsPublished()) {
			ObjectError error1 = new ObjectError("isPublished", "No puedes publicar un capítulo si tu historia aún no lo está.");
			result.addError(error1);
		}
		
		if(!(chapter.getIsPublished() != null)) {
			ObjectError error1 = new ObjectError("isPublished", "Debes indicar si va a ser público o no");
			result.addError(error1);
		}
		//----
		
		// Si al validarlo, encontramos errores:
		
		if (result.hasErrors()) {
			
			if(chapter.getIsPublished() == null) {
				modelMap.addAttribute("errorNullPublish", true);	
			}
		
			else {
				modelMap.addAttribute("errorNullPublish", false);	
			if(chapter.getIsPublished().equals(true) ) {
				modelMap.addAttribute("errorPublished", true);
			}else {
				modelMap.addAttribute("errorPublished", false);
			}
			}
			return VISTA_EDICION_chapter;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			chapter.setStory(story);
			chapterService.saveChapter(chapter);
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
				return VISTA_EDICION_chapter;
			}
			
			@PostMapping(value = "/chapters/{chapterId}/edit")
			public String processUpdateChapterForm(@Valid Chapter chapter, BindingResult result,
					@PathVariable("chapterId") int chapterId, 
					@PathVariable("storyId") int storyId, ModelMap model) {
				Story story = this.storyService.findStory(storyId);
				//No puedes hacer público un capítulo si las historia no esta publicada
				if(!(story.getStoryStatus().equals(StoryStatus.PUBLISHED)) && chapter.getIsPublished()) {
					ObjectError error1 = new ObjectError("isPublished", "No puedes publicar un capítulo si tu historia aún no lo está.");
					result.addError(error1);
				}
				//----
				if (result.hasErrors()) {
					if(chapter.getIsPublished().equals(true)) {
						model.addAttribute("errorPublished", true);
					}else {
						model.addAttribute("errorPublished", false);
					}
					return VISTA_EDICION_chapter;
				}
				else {
					chapter.setId(chapterId);
					chapter.setStory(story);
					this.chapterService.saveChapter(chapter);
//					return "redirect:/chapters/{chapterId}"; Aún no existe esta funcionalidad
					return "redirect:/";
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
				return "redirect:/";
			}
			
	
}

