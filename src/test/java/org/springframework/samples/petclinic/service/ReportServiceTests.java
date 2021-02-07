/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.ReportStatus;
import org.springframework.samples.petclinic.model.ReportType;
import org.springframework.samples.petclinic.repository.ChapterRepository;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.samples.petclinic.repository.StoryRepository;
import org.springframework.samples.petclinic.service.exceptions.ReportLimitException;


@ExtendWith(MockitoExtension.class)
class ReportServiceTests {        
    
	@Mock
	private ReportRepository reportRepository;
	

	protected ReportService reportService;
	
	@Mock
	private ChapterRepository chapterRepository;
	
	
	protected ChapterService chapterService;
	
	@Mock
	private StoryRepository storyRepository;
	
	
	protected StoryService storyService;
	
	@Mock
	protected AuthorService authorService;
	
	
	@BeforeEach
	void setup() {
		reportService = new ReportService(reportRepository, storyService);
		chapterService = new ChapterService(chapterRepository, authorService);
	}
    
	// Escenario positivo:
	
		// H13+E1 - Añadir reporte a capítulo que incumple las reglas de la comunidad.
		
	@Test
	@Transactional
	public void shouldInsertReport() throws DataAccessException, ReportLimitException {
		
		
					Report report = new Report();
					report.setId(1);
					report.setReportType(ReportType.COPYRIGHT_INFRINGEMENT);
					report.setReportStatus(ReportStatus.PENDING);
					report.setDate(LocalDate.now());
					report.setText("Te copias del rubiuh");
					
				
					

					Chapter c = chapterService.findChapterById(1);
					report.setChapter(c);
					
					Integer storyId = 1;
					
					when(reportRepository.save(report)).thenReturn(report);
					this.reportService.saveReport(report, storyId);
					verify(reportRepository).save(report);
					assertThat(report.getId()).isNotNull();
		

		
	}
	
		
		
		
		// H13-E1 - Añadir reporte vacío.
		
		
	@Test
	@Transactional
	public void shouldInsertReportEmpty()throws DataAccessException, ReportLimitException {
		
		Chapter c = chapterService.findChapterById(1);
		
		Report report = new Report();
		
	
		Integer storyId = 1;
		when(reportRepository.save(report)).thenReturn(report);
		this.reportService.saveReport(report, storyId);
		assertThat(report.getId()).isNull();
		assertThat(report.getReportType()).isNull();
		verify(reportRepository).save(report);
					
					
		
	}
			

			
		
}
		
