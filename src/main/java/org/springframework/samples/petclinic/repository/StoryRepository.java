package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Story;

public interface StoryRepository extends CrudRepository<Story, Integer>{
	
	@Query("SELECT story FROM Story story WHERE story.id  =:id")
	public Story findStoryById(@Param("id") int id);
	
	


}
