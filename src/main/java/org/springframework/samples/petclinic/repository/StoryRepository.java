package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Story;

public interface StoryRepository extends CrudRepository<Story, Integer>{

}
