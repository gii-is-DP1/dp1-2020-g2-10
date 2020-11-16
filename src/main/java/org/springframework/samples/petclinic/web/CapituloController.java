package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Autor;
import org.springframework.samples.petclinic.model.Capitulo;
import org.springframework.samples.petclinic.service.AutorService;
import org.springframework.samples.petclinic.service.CapituloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autores/{autorId}/historias/{historiaId}")
public class CapituloController {
	
	private final CapituloService capituloService;
	// private final HistoriaService historiaService;
	private final AutorService autorService;
	private static final String VISTA_EDICION_CAPITULO= "capitulo/editarCapitulo";
	
	@Autowired
	public CapituloController(CapituloService capituloService, /*HistoriaService historiaService,*/ AutorService autorService) {

		this.capituloService = capituloService;
		// this.historiaService = historiaService;
		this.autorService = autorService;
	}
	
	
	// HU-05: Añadir un capítulo a una historia.
	
	// En el primer método get, mostramos el formulario de edición del nuevo capítulo:
	@GetMapping("/capitulo/new")
	public String añadirCapitulo(Autor autor, ModelMap modelMap) {
		
		Capitulo cap = new Capitulo();
		modelMap.addAttribute("capitulo", cap);
		
		return VISTA_EDICION_CAPITULO;
		
	}
	
	// En este último post procesamos el capítulo recién creado. Lo validamos y se añade al listado de capítulos, si es correcto:
	@PostMapping("/capitulo/new")
	public String procesarNuevoCapitulo(Autor autor, @Valid Capitulo cap, BindingResult result, ModelMap modelMap) {
		
		String view = "capitulo/listadoCapitulos";
		
		// Si al validarlo, encontramos errores:
		
		if(result.hasErrors()) {
			
			modelMap.addAttribute("capitulo", cap);
			return VISTA_EDICION_CAPITULO;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			capituloService.saveCapitulo(cap);
			modelMap.addAttribute("mensajeExito", "¡El capítulo se ha añadido con éxito!");
		
		}
		
			return view;
		}
	
}

