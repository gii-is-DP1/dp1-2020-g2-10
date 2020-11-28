package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class StoryServiceTests {        
    @Autowired
	protected StoryService storyService;
        
    @Autowired
	protected AuthorService authorService;	


	@Test
	@Transactional
	public void shouldInsertStoryIntoDatabaseAndGenerateId() {
		Author author1 = this.authorService.findAuthorById(1);
		List<Story> storiesA1 = author1.getStories();
		int found = storiesA1.size();

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcione");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(Date.valueOf(LocalDate.of(03, 05, 2020)));
		story.setUrlCover("/resources/images/author-pictures/author1.jpg");
		author1.addStory(story);
		assertThat(author1.getStories().size()).isEqualTo(found + 1);

        this.storyService.saveStory(story);
		this.authorService.saveAuthor(author1);

		author1 = this.authorService.findAuthorById(1);
		assertThat(author1.getStories().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(story.getId()).isNotNull();
	}
	
//	@Test
//	@Transactional
//	public void shouldThrowExceptionInsertingStoryWithNoTitle() {
//		Owner owner6 = this.ownerService.findOwnerById(6);
//		Pet pet = new Pet();
//		pet.setName("wario");
//		Collection<PetType> types = this.petService.findPetTypes();
//		pet.setType(EntityUtils.getById(types, PetType.class, 2));
//		pet.setBirthDate(LocalDate.now());
//		owner6.addPet(pet);
//		
//		Pet anotherPet = new Pet();		
//		anotherPet.setName("waluigi");
//		anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
//		anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
//		owner6.addPet(anotherPet);
//		
//		try {
//			petService.savePet(pet);
//			petService.savePet(anotherPet);
//		} catch (DuplicatedPetNameException e) {
//			// The pets already exists!
//			e.printStackTrace();
//		}				
//			
//		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
//			anotherPet.setName("wario");
//			petService.savePet(anotherPet);
//		});		
//	}
//	
//	@Test
//	@Transactional
//	public void shouldThrowExceptionInsertingStoryWithEmptyValues() {
//		Owner owner6 = this.ownerService.findOwnerById(6);
//		Pet pet = new Pet();
//		pet.setName("wario");
//		Collection<PetType> types = this.petService.findPetTypes();
//		pet.setType(EntityUtils.getById(types, PetType.class, 2));
//		pet.setBirthDate(LocalDate.now());
//		owner6.addPet(pet);
//		
//		Pet anotherPet = new Pet();		
//		anotherPet.setName("waluigi");
//		anotherPet.setType(EntityUtils.getById(types, PetType.class, 1));
//		anotherPet.setBirthDate(LocalDate.now().minusWeeks(2));
//		owner6.addPet(anotherPet);
//		
//		try {
//			petService.savePet(pet);
//			petService.savePet(anotherPet);
//		} catch (DuplicatedPetNameException e) {
//			// The pets already exists!
//			e.printStackTrace();
//		}				
//			
//		Assertions.assertThrows(DuplicatedPetNameException.class, () ->{
//			anotherPet.setName("wario");
//			petService.savePet(anotherPet);
//		});		
//	}
//
//	@Test
//	@Transactional
//	public void shouldAddNewChapterForStory() {
//		Pet pet7 = this.petService.findPetById(7);
//		int found = pet7.getVisits().size();
//		Visit visit = new Visit();
//		pet7.addVisit(visit);
//		visit.setDescription("test");
//		this.petService.saveVisit(visit);
//            try {
//                this.petService.savePet(pet7);
//            } catch (DuplicatedPetNameException ex) {
//                Logger.getLogger(PetServiceTests.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//		pet7 = this.petService.findPetById(7);
//		assertThat(pet7.getVisits().size()).isEqualTo(found + 1);
//		assertThat(visit.getId()).isNotNull();
//	}
}
