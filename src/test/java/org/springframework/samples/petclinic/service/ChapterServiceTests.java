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

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ChapterServiceTests {        
    
	@Autowired
	protected ChapterService chapterService;
	
	@Autowired
    protected StoryService storyService;
	
	@Autowired
	protected AuthorService authorService;
    
	// Escenario positivo:
	
		// H5+E1 - Añadir un nuevo capítulo mi historia.
		
		@Test
		@Transactional
		public void shouldInsertChapter() {
			

			// Almacenamos en la colección los capítulos que forman parte de la historia.
			Collection<Chapter> chaptersOfStory = this.chapterService.findChapterByStoryId(1);

			// Registramos el número de capítulos.
			int nChapters = chaptersOfStory.size();

			// Creamos un capítulo nuevo.
			Chapter chapter = new Chapter();
			chapter.setId(20);
			chapter.setIndex(20);
			chapter.setTitle("Divangando en el sendero eterno del sueño");
			chapter.setBody("El hombre condenado sin esperanza" + "Por creerse Dios, a su imagen y semejanza"
					+ "A mi no me trata ni Dios ni la Iglesia"
					+ "Guardo recuerdos inconfesables que no borra ni la amnesia");
			chapter.setIsPublished(true);
		
			
			// Instauramos historia para emplearla en la prueba.

			Story s = storyService.findStoryById(1);
			chapter.setStory(s);

			// Probamos si se inserta correctamente.
			this.chapterService.saveChapter(chapter);

			// Comprobamos que el id del nuevo capítulo es distinto de cero.
			
			assertThat(chapter.getId().longValue()).isNotEqualTo(0);

			// Finalmente, verificamos si el tamaño es el de antes de añadir el capítulo más
			// el nuevo.
			chaptersOfStory = this.chapterService.findChapterByStoryId(1);
			assertThat(chaptersOfStory.size()).isEqualTo(nChapters + 1);
		}
		
		// H5+E2-Añadir un nuevo capítulo coautor.
		
		
	 // Escenarios negativos:
		// H5-E1 - No añadir un nuevo capítulo como editor.
		
		@Test
		@WithMockUser(value = "author2", authorities = {
		        "author"
		    })
		@Transactional
		public void attempInsertChapterWithinAuthorOfStory() {
			
			// Definimos autor:
						Author au = authorService.findAuthorById(2);
					

						// Creamos un capítulo nuevo.
						Chapter chapter = new Chapter();
						chapter.setId(20);
						chapter.setIndex(20);
						chapter.setTitle("Divangando en el sendero eterno del sueño");
						chapter.setBody("El hombre condenado sin esperanza" + "Por creerse Dios, a su imagen y semejanza"
								+ "A mi no me trata ni Dios ni la Iglesia"
								+ "Guardo recuerdos inconfesables que no borra ni la amnesia");
						chapter.setIsPublished(true);
					
						
						// Instauramos historia para emplearla en la prueba.

						Story s = storyService.findStoryById(1);
						chapter.setStory(s);

						// Probamos que salta excepción.
						this.chapterService.saveChapter(chapter);
			if(s.getAuthor().equals(au)) {
		    Exception exception = assertThrows(NumberFormatException.class, () -> {
		        Integer.parseInt("1a");
		    });
		 
		    String expectedMessage = "No puedes insertar capítulo si no eres autor o coautor de la historia.";
		    String actualMessage = exception.getMessage();
		 
		    assertTrue(actualMessage.contains(expectedMessage));
		}
		}
	
	//----------------------------------------------------------------------------------------------------------------	
	@Test
	@Transactional
	void shouldUpdateChapter() {
		// Cambiamos el titulo de chapter, y comprobamos que se ha efectuado correctamente el cambio
		Chapter chapter = this.chapterService.findChapterById(1);
		String oldTitle = chapter.getTitle();
		String newTitle = oldTitle + " un cambio cualquiera";

		chapter.setTitle(newTitle); //Cambiamos el titulo
		
		this.chapterService.saveChapter(chapter);

		chapter = this.chapterService.findChapterById(1);
		assertThat(chapter.getTitle()).isEqualTo(newTitle);
	}
	
	//Tests HU16
	@Test
	void shouldFindChapters() {
		
		Collection<Chapter> chapters = this.chapterService.findChapters(1);
		assertThat(chapters.size()).isEqualTo(3);
		
		Chapter chapter = EntityUtils.getById(chapters, Chapter.class, 1);
		assertThat(chapter.getTitle()).isEqualTo("Principium");
		assertThat(chapter.getBody()).isEqualTo("Integer porttitor nulla id felis maximus pharetra. Etiam at neque eu justo "
				+ "placerat cursus. Proin blandit eu justo ac gravida. Proin ac metus sed dui.");
		assertThat(chapter.getIsPublished()).isEqualTo(true);
		assertThat(chapter.getStory().getId()).isEqualTo(1);
	}
}
