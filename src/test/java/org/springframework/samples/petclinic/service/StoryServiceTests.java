package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Genre;
import org.springframework.samples.petclinic.model.Story;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class StoryServiceTests {        
    @Autowired
	protected StoryService storyService;
        
    @Autowired
	protected AuthorService authorService;	

    
    
	//@Test
	@Transactional
	public void shouldInsertStoryIntoDatabaseAndGenerateId() {
		Author author1 = this.authorService.findAuthorById(1);
//		List<Story> storiesA1 = author1.getStories();
		int found = 2;

		Story story = new Story();
		story.setTitle("La prueba positiva");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que funcione");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.PUBLISHED);
		story.setUpdatedDate(new Date());
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
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean =
				new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	@WithMockUser(value = "author1", authorities = {
	        "author"
	    })
	@Transactional
	public void shouldNotInsertStoryIntoDatabaseBecauseTittleIsEmpty() {
		Author author1 = this.authorService.findAuthorById(1);
		List<Story> storiesA1 = storyService.getStoriesFromAuthorId(author1.getId()).stream().collect(Collectors.toList());
		int found = storiesA1.size();

		Story story = new Story();
		story.setTitle("");
		story.setGenre(Genre.CHILDREN_STORY);
		story.setDescription("Espero que no funcioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
		story.setIsAdult(false);
		story.setStoryStatus(StoryStatus.DRAFT);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Story>> constraintViolations = validator.validate(story);
		assertThat(constraintViolations.size()).isEqualTo(2);
		Iterator<ConstraintViolation<Story>> it = constraintViolations.iterator();
//		ConstraintViolation<Story> violation = constraintViolations.iterator().next();
		ConstraintViolation<Story> violation = it.next();
		assertThat(violation.getPropertyPath().toString())
							.isEqualTo("title");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");

        this.storyService.saveStory(story);
		this.authorService.saveAuthor(author1);

		author1 = this.authorService.findAuthorById(1);
		assertThat(storyService.getStoriesFromAuthorId(author1.getId()).size()).isEqualTo(found);
		// checks that id has not been generated
		assertThat(story.getId()).isNull();
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
	
	//@SuppressWarnings({ "deprecation", "static-access" })
	@Test
	void shouldFindStories()  {
		Collection<Story> stories = this.storyService.findStories();

		Story story = EntityUtils.getById(stories, Story.class, 1);
		assertThat(story.getTitle()).isEqualTo("Lorem ipsum");
		assertThat(story.getDescription()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum "
				+ "at blandit dolor, at laoreet nulla. Donec nibh nisi.");
		assertThat(story.getDedication()).isEqualTo("Dicata latine loquentium");
		Genre g= Genre.NOVEL;
		assertThat(story.getGenre()).isEqualTo(g);
		StoryStatus s= StoryStatus.PUBLISHED;
		assertThat(story.getStoryStatus()).isEqualTo(s);
		Boolean b= Boolean.FALSE;
		assertThat(story.getIsAdult()).isEqualTo(b);
		//SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
		//Date d = sdf.parse("2020-10-12 15:00");
//		Date d = new Date(120, 9, 12, 15, 00);
		
//		d.parse("yyyy/MM/dd HH:mm");
//		d.parse("yyyy/MM/dd HH:mm");
		
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println("---------------------------------------------------------------");
//		System.out.println(story.getUpdatedDate());
//		assertThat(story.getUpdatedDate()).isEqualTo("2020-10-12 15:00");

	}
}
