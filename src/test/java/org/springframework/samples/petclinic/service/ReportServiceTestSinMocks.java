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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.ReportStatus;
import org.springframework.samples.petclinic.model.ReportType;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ReportServiceTestSinMocks {        
    
	@Autowired
	protected ReportService reportService;
	
	@Autowired
	protected ChapterService chapterService;
	
	@Autowired
    protected StoryService storyService;
	
	@Autowired
	protected AuthorService authorService;
    
	// Escenario positivo:
	
		// H13+E1 - Añadir un nuevo reporte a capítulo.
		
		@Test
		@Transactional
		public void shouldInsertReport() {
			
			Integer storyId = 1;
			
			Collection<Report> reportsOfChapter = this.reportService.findReportByChapterId(1);

			
			int nReports = reportsOfChapter.size();


			Report report = new Report();
			report.setId(1);
			report.setReportType(ReportType.COPYRIGHT_INFRINGEMENT);
			report.setReportStatus(ReportStatus.PENDING);
			report.setDate(LocalDate.now());
			report.setText("Te copias del rubiuh");
			
		

			Chapter c = chapterService.findChapterById(1);
			report.setChapter(c);

			
			this.reportService.saveReport(report, storyId);

			
			
			assertThat(report.getId().longValue()).isNotEqualTo(0);

			
			reportsOfChapter = this.reportService.findReportByChapterId(1);
			assertThat(reportsOfChapter .size()).isEqualTo(nReports + 1);
		}
		
		

		// Escenarios negativos:
				// H5-E1 - No añadir un reporte vacio.
				
		@Test
		@Transactional
		public void attempInsertReportEmpty() {
			
			Integer storyId = 1;
			
			Collection<Report> reportsOfChapter = this.reportService.findReportByChapterId(1);

			
			int nReports = reportsOfChapter.size();
					Report report = new Report();
					report.setId(null);
					report.setReportType(null);
					report.setReportStatus(null);
					report.setDate(null);
					report.setText("");
					
					Chapter c = chapterService.findChapterById(1);
					report.setChapter(c);
								
								
								
								
				Exception exception = assertThrows(ConstraintViolationException.class, () -> {

					this.reportService.saveReport(report, storyId);

				});

				assertEquals(exception.getMessage().contains("no puede estar vacío"), true);
								
					
				   
				}
	
	

	
}
	