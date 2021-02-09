package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Contribution;
import org.springframework.samples.petclinic.model.ContributionType;

import org.springframework.samples.petclinic.model.Story;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ContributionServiceTests {
	
	
	

	@Autowired
	protected StoryService storyService;

	@Autowired
	protected AuthorService authorService;
	
	@Autowired
	protected ContributionService contributionService;
	
	@Autowired
	protected CompanyService companyService;
	
	 
		@Test
		@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
		public void shouldCreateContributionAndGenerateId() {
			Author author1 = this.authorService.findAuthorById(1);
			Story s1 = this.storyService.findStoryById(1);
			//Contribution contribution = contributionService.createContribution(s1);
		
			
			//assertThat(contribution).isNotNull();
		}
	
		
		@Test
		@WithMockUser(value = "author1", authorities = {
		        "author"
		    })
		@Transactional
		public void shouldInsertContributionIntoDatabaseAndGenerateId() {

			Contribution contribution = new Contribution();
			Author author1 = authorService.findAuthorById(1);
			Story story1 = storyService.findStoryById(1);
			contribution.setStory(story1);
			contribution.setAuthor(author1);
			contribution.setContributionType(ContributionType.COAUTHOR);
	        this.contributionService.saveContribution(contribution);

			// checks that id has been generated
			
			assertThat(contribution.getId()).isNotNull();
		}
		
	
		

		
		

}