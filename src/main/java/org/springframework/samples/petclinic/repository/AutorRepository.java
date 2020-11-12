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
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Autor;
import org.springframework.samples.petclinic.repository.AutorRepository;

/**
 * Spring Data JPA AutorRepository interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface AutorRepository extends Repository<Autor, Integer> {

	/**
	 * Save an <code>Autor</code> to the data store, either inserting or updating it.
	 * @param Autor the <code>Autor</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(Autor autor) throws DataAccessException;

	/**
	 * Retrieve <code>Autor</code>s from the data store by last name, returning all Autors
	 * whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a <code>Collection</code> of matching <code>Autor</code>s (or an empty
	 * <code>Collection</code> if none found)
	 */	
	@Query("SELECT DISTINCT autor FROM Autor autor WHERE autor.lastName LIKE :lastName%")
	public Collection<Autor> findByLastName(@Param("lastName") String lastName);


	/**
	 * Retrieve an <code>Autor</code> from the data store by id.
	 * @param id the id to search for
	 * @return the <code>Autor</code> if found
	 * @throws org.springframework.dao.DataRetrievalFailureException if not found
	 */	
	@Query("SELECT autor FROM Autor autor WHERE autor.id =:id")
	public Autor findById(@Param("id") int id);

}