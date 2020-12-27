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
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.repository.CompanyRepository;

/**
 * Spring Data JPA AutorRepository interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface CompanyRepository extends Repository<Company, Integer> {


	void save(Company editorial) throws DataAccessException;

	@Query("SELECT DISTINCT company FROM Company company WHERE company.name LIKE :name%")
	public Collection<Company> findByName(@Param("name") String name);

	@Query("SELECT company FROM Company company WHERE company.id =:id")
	public Company findById(@Param("id") int id);
	
	@Query("SELECT DISTINCT company FROM Company company WHERE company.user.username = :username")
	public Optional<Company> findByUserUsername(@Param("username") int username);

}
