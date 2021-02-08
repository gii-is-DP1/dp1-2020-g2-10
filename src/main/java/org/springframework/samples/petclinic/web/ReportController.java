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
import org.springframework.samples.petclinic.service.exceptions.ReportLimitException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			
	
	@GetMapping("/reports/new")
	public String initAddReport(ModelMap modelMap) {
		modelMap.put("buttonCreate", true);
		Report report = new Report();
		report.setReportStatus(ReportStatus.PENDING);
		report.setDate(LocalDate.now());
		modelMap.put("report", report);
		
		
		return VIEW_EDIT_REPORT;
		
	}
	
	
	@PostMapping("/reports/new")
	public String processNewReport(@PathVariable("storyId") int storyId, @PathVariable("chapterId") int chapterId, @Valid Report report, 
			BindingResult result, ModelMap modelMap, RedirectAttributes redirectAttributes) throws ReportLimitException{
		
		modelMap.put("buttonCreate", true);
		
		
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
		
		
		else { 
			try {
				Chapter chapter = this.chapterService.findChapterById(chapterId);
				
				report.setChapter(chapter);
				reportService.saveReport(report, storyId);
			
				modelMap.addAttribute("messageSuccess", "¡El reporte se ha enviado con éxito!");
				return "redirect:/stories/{storyId}/chapters/{chapterId}";
				
			} catch (ReportLimitException ex) {
				result.rejectValue("text", "TooManyReports" ,"The story associated to this chapter has already been reported 3 times.");
				return VIEW_EDIT_REPORT;
			}
		}
		
		
		
		
			
		
		}
	
	
		
}

