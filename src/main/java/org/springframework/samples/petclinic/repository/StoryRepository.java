package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Story;

public interface StoryRepository extends CrudRepository<Story, Integer>{
	
//	@Query("SELECT story FROM Story story WHERE story.id  =:id")
//	public Story findStoryById(@Param("id") int id);

	public Collection<Story> findAll() throws DataAccessException;
	
	@Query("SELECT story FROM Story story WHERE author.id  =:authorId")
	public Collection<Story> getStoriesFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
}
