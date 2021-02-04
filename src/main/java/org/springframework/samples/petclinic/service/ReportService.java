package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.ReportStatus;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportService {

	private ReportRepository reportRepository;	
	private StoryService storyService;
	
	@Autowired
	public ReportService(ReportRepository reportRepository, StoryService storyService) {
		this.reportRepository = reportRepository;
		this.storyService = storyService;
		
	}	
	
	public Report findReportById(int reportId){
		return reportRepository.findReportById(reportId);
	}	
	
	
	public int countReportOfStoryId(int storyId) {
		return reportRepository.countReportOfStoryId(storyId);
	}

	@Transactional
	public void saveReport(@Valid Report report, int storyId) throws DataAccessException {
		
		// Creamos el reporte
		report.setReportStatus(ReportStatus.PENDING);
		report.setDate(LocalDate.now());
		reportRepository.save(report);	
		int numeroReportesHistoria = reportRepository.countReportOfStoryId(storyId);
		
		if(numeroReportesHistoria == 3) {

		
		storyService.updateStory(storyId);
		
		}
		
		
	}		
	
	public Collection<Report> findReportByChapterId(int chapterId){
		return reportRepository.findReportByChapterId(chapterId);
	}		
	

}

