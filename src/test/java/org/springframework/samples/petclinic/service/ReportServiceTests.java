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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;

import org.assertj.core.api.ThrowableTypeAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.ReportStatus;
import org.springframework.samples.petclinic.model.ReportType;
import org.springframework.samples.petclinic.repository.ChapterRepository;
import org.springframework.samples.petclinic.repository.ReportRepository;


@ExtendWith(MockitoExtension.class)
class ReportServiceTests {        
    
	@Mock
	private ReportRepository reportRepository;
	
	
	protected ReportService reportService;
	
	@Mock
	private ChapterRepository chapterRepository;
	
	
	protected ChapterService chapterService;
	
	@Mock
	protected AuthorService authorService;
	
	
	@BeforeEach
	void setup() {
		reportService = new ReportService(reportRepository);
		chapterService = new ChapterService(chapterRepository, authorService);
	}
    
	// Escenario positivo:
	
		// H13+E1 - Añadir reporte a capítulo que incumple las reglas de la comunidad.
		
	@Test
	public void shouldInsertReport() {
		
		// Creamos un capítulo nuevo.
					Report report = new Report();
					report.setId(1);
					report.setReportType(ReportType.COPYRIGHT_INFRINGEMENT);
					report.setReportStatus(ReportStatus.PENDING);
					report.setDate(LocalDate.now());
					report.setText("Te copias del rubiuh");
					
				
					// Instauramos capítulo para emplearla en la prueba.

					Chapter c = chapterService.findChapterById(1);
					report.setChapter(c);
					
					when(reportRepository.save(report)).thenReturn(report);
					
					
					this.reportService.saveReport(report);
					
					//verify(reportService).saveReport(report);
					assertThat(report.getId()).isNotNull();
		

		
	}
	
		
		
		
		// H13-E2 - Añadir reporte vacío.
		
		
	@Test
	public void shouldInsertReportEmpty() {
		
		// Creamos un capítulo nuevo.
					Report report = new Report();
				
					when(reportRepository.save(report)).thenReturn(report);
					
					this.reportService.saveReport(report);
					this.reportService.saveReport(report);
					
					
					assertThat(report.getId()).isNull();
		

		
	}
	
			

			
		
}
		
