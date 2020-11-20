package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "stories")
public class Story extends BaseEntity{
	
	@NotEmpty
	@Column(name = "title")
	private String title;
	
	@Length(min=120)
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@Column(name = "dedication")
	private String dedication;
	
	
	@NotNull
	@Column(name = "genre")
	private Genre genre;
	
//	COMENTADO PORQUE SI SE DEJA PUESTO PETA
//	@NotNull
//	@UniqueElements
//	private Collection<Tag> tags;
	
	@NotNull
	@Column(name = "storyStatus")
	private StoryStatus storyStatus;
	
	@NotNull
	@Column(name = "isAdult")
	private Boolean isAdult;
	
	@NotNull
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Column(name = "update_date")
	private Date updatedDate; 
	
	@ManyToOne(optional=false)
	@Column(name = "author_id")
	private Author author;
	
	

}
