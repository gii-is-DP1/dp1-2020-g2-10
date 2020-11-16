package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Chapter;

public interface ChapterRepository extends CrudRepository<Chapter, Integer>{

}
