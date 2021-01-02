package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Chapter;

public interface ChapterRepository extends CrudRepository<Chapter, Integer>{

	// Recogemos los capítulos que componen la historia correspondiente al id introducido en la consulta
	@Query("SELECT chapter FROM Chapter chapter WHERE chapter.story.id  =:id")
	public Collection<Chapter> findChapterByStoryId(@Param("id") int id);
	
	@Query("SELECT chapter FROM Chapter chapter WHERE chapter.id  =:id")
	public Chapter findChapterById(@Param("id") int id);
			
}
