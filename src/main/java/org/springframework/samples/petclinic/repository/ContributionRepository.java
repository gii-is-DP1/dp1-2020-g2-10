package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.Contribution;
import org.springframework.samples.petclinic.model.Story;

public interface ContributionRepository extends CrudRepository<Contribution, Integer>{
	
//	public Collection<Contribution> findAll() throws DataAccessException;
//	
//	@Query("SELECT contribution FROM Contribution contribution WHERE contribution.author.id  =:authorId")
//	public Collection<Contribution> getContributionsFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
//	
//	@Query("SELECT contribution FROM Contribution contribution WHERE contribution.story.id  =:storyId")
//	public Collection<Contribution> getContributionsFromStoryId(@Param("storyId") int storyId) throws DataAccessException;
//	
//	//Contribuciones de coautor H12
//	@Query("SELECT contribution FROM Contribution contribution WHERE contribution.contributionType LIKE 'COAUTHOR' AND contribution.author.id = :authorId AND contribution.story.id = :storyId")
//	public Collection<Contribution> findCoautorContribution(@Param("authorId") int authorId,@Param("storyId") int storyId);
	
}
