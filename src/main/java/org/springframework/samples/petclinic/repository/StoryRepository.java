package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;



public interface StoryRepository extends CrudRepository<Story, Integer>{
	
	@Query("SELECT story FROM Story story WHERE story.storyStatus  like 'PUBLISHED' or (:authorId is not null and story.author.id  =:authorId and story.storyStatus like 'DRAFT')")
	public Collection<Story> findStory(@Param("authorId") Integer authorId);

	//public Collection<Story> findAll() throws DataAccessException;
	
	@Query("SELECT story FROM Story story WHERE author.id  =:authorId")
	public Collection<Story> getStoriesFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
	

	@Modifying
	@Query("UPDATE Story s SET s.storyStatus = ?1 where s.id = ?2")
	public int setStoryStatus(StoryStatus storyStatus, int storyId);
	
	

	@Query("SELECT COUNT (*) FROM Story story WHERE (story.storyStatus = ?1 and story.author.id = ?2)")
	public Integer countReviewStories(StoryStatus status, Integer authorId) throws DataAccessException;
		
	
	@Query("SELECT story FROM Story story WHERE story.id  =:storyId")
	public Story getStoryFromId(@Param("storyId") int soryId) throws DataAccessException;

}
