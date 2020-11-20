package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors/{authorId}/story/{storyId}")
public class ChapterController {
	
	private final ChapterService chapterService;
	// private final StoryService storyService;
	private final AuthorService authorService;
	private static final String VISTA_EDICION_CHAPTER= "chapter/editChapter";
	
	@Autowired
	public ChapterController(ChapterService chapterService, /*StoryService storyService,*/ AuthorService authorService) {

		this.chapterService = chapterService;
		// this.storyService = storyService;
		this.authorService = authorService;
	}
	
	
	// HU-05: Añadir un capítulo a una historia.
	
	// En el primer método get, mostramos el formulario de edición del nuevo capítulo:
	@GetMapping("/chapter/new")
	public String initAddChapter(Author author, ModelMap modelMap) {
		
		Chapter chapter = new Chapter();
		modelMap.addAttribute("chapter", chapter);
		
		return VISTA_EDICION_CHAPTER;
		
	}
	
	// En este último post procesamos el capítulo recién creado. Lo validamos y se añade al listado de capítulos, si es correcto:
	@PostMapping("/chapter/new")
	public String processNewChapter(@Valid Chapter cap, BindingResult result, ModelMap modelMap) {
		
		String view = "chapter/listChapters";
		
		// Si al validarlo, encontramos errores:
		
		if(result.hasErrors()) {
			
			modelMap.addAttribute("chapter", cap);
			return VISTA_EDICION_CHAPTER;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			chapterService.saveChapter(cap);
			modelMap.addAttribute("messageSuccess", "¡El capítulo se ha añadido con éxito!");
		
		}
		
			return view;
		}
	
}

