package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Chapter;
import org.springframework.samples.petclinic.model.StoryStatus;
import org.springframework.samples.petclinic.repository.ChapterRepository;
import org.springframework.samples.petclinic.service.exceptions.CannotPublishException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChapterService {

	private ChapterRepository chapterRepository;
	
	private AuthorService authorService;
	
	private StoryService storyService;
	
	@Autowired
	public ChapterService(ChapterRepository chapterRepository, AuthorService authorService) {
		this.chapterRepository = chapterRepository;
		this.authorService = authorService;
	}	
	
	@Transactional(readOnly = true)	
	public Collection<Chapter> findChapters(Integer storyId) throws DataAccessException {
		return chapterRepository.findChapterByStoryId(storyId);
	}	

	@Transactional
	public Chapter findChapterById(Integer chapterId) throws DataAccessException {
		Chapter chapter = chapterRepository.findChapterById(chapterId);
		return chapter;
	}

	@Transactional//(rollbackFor = CannotPublishException.class)
	public void saveChapter(@Valid Chapter chapter) throws DataAccessException{//, CannotPublishException {
		
		// Creamos el capítulo
		
		//REGLA DE NEGOCIO 2
//		Integer authorId = this.authorService.getPrincipal().getId();
//		Integer reviewStories = this.storyService.countReviewStories(StoryStatus.REVIEW, authorId);
//		if(reviewStories>=3) {
//			throw new CannotPublishException();
//		}else {
			chapterRepository.save(chapter);		
//		}
		
		
	}
	
	@Transactional
	public void deleteChapter(int chapterId) {
		Chapter chapter = findChapterById(chapterId);
		Author principalAuthor = authorService.getPrincipal();
		
		assertNotNull(
				String.format("No chapter with ID=%d was found for deletion", chapterId),
				chapter);
		assertTrue("Only the main author creator of the story can delete a chapter in it.",
				chapter.getStory().getAuthor().equals(principalAuthor));
		assertFalse("Only chapters as DRAFT can be deleted", chapter.getIsPublished());
		chapterRepository.delete(chapter);
	}
	
	public Collection<Chapter> findChapterByStoryId(int storyId){
		return chapterRepository.findChapterByStoryId(storyId);
	}		

}

