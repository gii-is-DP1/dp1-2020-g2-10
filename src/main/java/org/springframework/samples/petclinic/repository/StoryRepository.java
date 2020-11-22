package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Story;

public interface StoryRepository extends CrudRepository<Story, Integer> {

	public Collection<Story> findAll() throws DataAccessException;

	Story findStoryById(int id) throws DataAccessException;


}
