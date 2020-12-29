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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.CompanyRepository;
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

	@Autowired
	private CompanyRepository companyRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Transactional(readOnly = true)
	public Optional<Company> findCompanyById(int id) {
		Optional<Company> res = companyRepository.findById(id);
		
		return res;
	}
	
	
	public Company getPrincipal(){
		Company res = null;
		
		User currentUser = userService.getPrincipal();
		if(currentUser != null) {
			Optional<Company> optionalCompany = companyRepository.findByUserUsername(currentUser.getUsername());
			if(optionalCompany.isPresent()) {
				res = optionalCompany.get();
			}
		}
		return res;
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
