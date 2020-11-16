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
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Moderator;
import org.springframework.samples.petclinic.repository.AuthorRepository;
import org.springframework.samples.petclinic.repository.CompanyRepository;
import org.springframework.samples.petclinic.repository.ModeratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class CompanyService {

	private CompanyRepository companyRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}	

	@Transactional(readOnly = true)
	public Company findCompanyById(int id) throws DataAccessException {
		return companyRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Company> findCompanyByLastName(String name) throws DataAccessException {
		return companyRepository.findByName(name);
	}

	@Transactional
	public void saveCompany(Company company) throws DataAccessException {
		//creating company
		companyRepository.save(company);		
		//creating user
		userService.saveUser(company.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(company.getUser().getUsername(), "company");
	}		

}
