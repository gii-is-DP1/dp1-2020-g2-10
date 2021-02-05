package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.ReportStatus;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.ChapterService;
import org.springframework.samples.petclinic.service.ReportService;
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
@RequestMapping("/stories/{storyId}/chapters/{chapterId}")
public class ReportController {
	
	private final ReportService reportService;
	private final ChapterService chapterService;
	private final StoryService storyService;
	
	
	private static final String VIEW_EDIT_REPORT= "reports/editReport";
	
	@Autowired
	public ReportController(ReportService reportService, ChapterService chapterService, StoryService storyService) {

		this.reportService = reportService;
		this.chapterService = chapterService;
		this.storyService = storyService;
		
	}
	
	@InitBinder
 	public void setAllowedFields(WebDataBinder dataBinder) {
 		dataBinder.setDisallowedFields("id");
 	}
			
	
		
	
	// HU-05: Añadir un capítulo a una historia.
	
	// En el primer método get, mostramos el formulario de edición del nuevo capítulo:
	@GetMapping("/reports/new")
	public String initAddReport(ModelMap modelMap) {
		modelMap.put("buttonCreate", true);
		Report report = new Report();
		modelMap.put("report", report);
		
		
		return VIEW_EDIT_REPORT;
		
	}
	
	// En este último post procesamos el capítulo recién creado. Lo validamos y se añade al listado de capítulos, si es correcto:
	@PostMapping("/reports/new")
	public String processNewReport(@PathVariable("storyId") int storyId, @PathVariable("chapterId") int chapterId, @Valid Report report, BindingResult result, ModelMap modelMap) {
		
		modelMap.put("buttonCreate", true);
		
		// Si al validarlo, encontramos errores:
		if(!(report.getReportType() != null)) {
			ObjectError error1 = new ObjectError("reportType", "Debe señalar el tipo de reporte");
			result.addError(error1);
		}
		
		
		
		if(result.hasErrors()) {
			if(report.getReportType() == null) {
				modelMap.addAttribute("errorReportType", true);
			}else {
				modelMap.addAttribute("errorReportType", false);
			}
			
			return VIEW_EDIT_REPORT;
		}
		
		// Si al validarlo, no hallamos ningún error:
		
		else { 
			
			
			Chapter chapter = this.chapterService.findChapterById(chapterId);
			report.setChapter(chapter);
			reportService.saveReport(report, storyId);
			
			modelMap.addAttribute("messageSuccess", "¡El reporte se ha enviado con éxito!");
			return "redirect:/stories/{storyId}/chapters/{chapterId}";
		
		}
		
		
		
		
			
		
		}
	
	
		
}

