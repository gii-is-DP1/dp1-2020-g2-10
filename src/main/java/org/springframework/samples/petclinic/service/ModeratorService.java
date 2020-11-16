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
import org.springframework.samples.petclinic.model.Moderator;
import org.springframework.samples.petclinic.repository.AuthorRepository;
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
public class ModeratorService {

	private ModeratorRepository moderatorRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	public ModeratorService(ModeratorRepository ModeratorRepository) {
		this.moderatorRepository = ModeratorRepository;
	}	

	@Transactional(readOnly = true)
	public Moderator findModeratorById(int id) throws DataAccessException {
		return moderatorRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Moderator> findModeratorByLastName(String lastName) throws DataAccessException {
		return moderatorRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveModerator(Moderator moderator) throws DataAccessException {
		//creating Moderator
		moderatorRepository.save(moderator);		
		//creating user
		userService.saveUser(moderator.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(moderator.getUser().getUsername(), "moderator");
	}		

}
