package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;

public interface StoryRepository extends CrudRepository<Story, Integer>{
	
	@Query("SELECT story FROM Story story WHERE story.storyStatus  =:published or story.storyStatus =:draft")
	public Collection<Story> findStory(@Param("published") StoryStatus storyStatus1, @Param("draft") StoryStatus storyStatus2);

	//public Collection<Story> findAll() throws DataAccessException;
	
	@Query("SELECT story FROM Story story WHERE author.id  =:authorId")
	public Collection<Story> getStoriesFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
}
