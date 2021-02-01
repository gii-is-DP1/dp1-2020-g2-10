package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

	private ReportRepository reportRepository;	
	
	@Autowired
	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}	
	
	public Report findReportById(int reportId){
		return reportRepository.findReportById(reportId);
	}	

	public void saveReport(@Valid Report report) throws DataAccessException {
		
		// Creamos el reporte
		reportRepository.save(report);		
		
	}		
	
	public Collection<Report> findReportByChapterId(int chapterId){
		return reportRepository.findReportByChapterId(chapterId);
	}		
	

}

