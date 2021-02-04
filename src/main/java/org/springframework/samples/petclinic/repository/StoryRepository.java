package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;

public interface StoryRepository extends CrudRepository<Story, Integer>{
	
//	@Query("SELECT story FROM Story story WHERE story.id  =:id")
//	public Story findStoryById(@Param("id") int id);

	public Collection<Story> findAll() throws DataAccessException;
	
	@Query("SELECT story FROM Story story WHERE author.id  =:authorId")
	public Collection<Story> getStoriesFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
	
	@Query("SELECT COUNT (*) FROM Story story WHERE (story.storyStatus = ?1 and story.author.id = ?2)")
	public Integer countReviewStories(StoryStatus status, Integer authorId) throws DataAccessException;
		
}
