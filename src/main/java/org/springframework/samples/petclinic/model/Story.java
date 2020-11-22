package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
public @Data class Story extends BaseEntity{
	
	@NotEmpty
	private String title;
	
	@Length(min=120)
	@NotEmpty
	private String description;
	
	private String dedication;
	
	
	@NotNull
	private Genre genre;
	
//	COMENTADO PORQUE SI SE DEJA PUESTO PETA
//	@NotNull
//	@UniqueElements
//	private Collection<Tag> tags;
	
	@NotNull
	private StoryStatus storyStatus;
	
	@NotNull
	private Boolean isAdult;
	
	@NotNull
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private Date updatedDate; 
	
	@ManyToOne(optional=false)
	private Author author;
	
	

}
