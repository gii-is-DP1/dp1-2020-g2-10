package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	
	@Query("SELECT review FROM Review review WHERE author.id  =:authorId")
	public Collection<Review> getReviewsFromAuthorId(@Param("authorId") int authorId) throws DataAccessException;
	
}
