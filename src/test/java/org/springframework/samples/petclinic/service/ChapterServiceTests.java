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

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ChapterServiceTests {        
    
	@Autowired
	protected ChapterService chapterService;
	
	@Autowired
    protected StoryService storyService;
        
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
}
