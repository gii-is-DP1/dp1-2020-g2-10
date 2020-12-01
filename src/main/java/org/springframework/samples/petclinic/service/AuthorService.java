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
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class AuthorService {

	private AuthorRepository authorRepository;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;

	public AuthorService(AuthorRepository authorRepository, UserService userService,
			AuthoritiesService authoritiesService) {
		super();
		this.authorRepository = authorRepository;
		this.userService = userService;
		this.authoritiesService = authoritiesService;
	}

	@Transactional(readOnly = true)
	public Author findAuthorById(int id) throws DataAccessException {
		return authorRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Author> findAuthorByLastName(String lastName) throws DataAccessException {
		return authorRepository.findByLastName(lastName);
	}
	
	/* Devuelve el actor autenticado y null en otro caso*/
	@Transactional(readOnly = true)
	public Author getPrincipal(){
		Author res = null;
		
		User currentUser = userService.getPrincipal();
		if(currentUser != null) {
			Optional<Author> optionalAuthor = authorRepository.findByUserUsername(currentUser.getUsername());
			if(optionalAuthor.isPresent()) {
				res = optionalAuthor.get();
			}
		}
		return res;
	}
	
	@Transactional(readOnly = true)
	public Boolean isPrincipalAuthor() {
		return userService.getPrincipalRole().equals("author");
	}

	@Transactional
	public void saveAuthor(Author author) throws DataAccessException {
		//creating author
		authorRepository.save(author);		
		//creating user
		userService.saveUser(author.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(author.getUser().getUsername(), "author");
	}		

}
