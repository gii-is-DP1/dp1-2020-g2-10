package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "stories")
public @Data class Story extends BaseEntity{
	
	@NotEmpty
	private String title;
	
	@Length(min=120)
	@NotEmpty
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String dedication;
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Genre genre;
	
//	COMENTADO PORQUE SI SE DEJA PUESTO PETA
//	@NotNull
//	@UniqueElements
//	private Collection<Tag> tags;
	
	@NotNull
	@Column(name = "story_status")
	@Enumerated(EnumType.STRING)
	private StoryStatus storyStatus;
	
	@NotNull
	@Column(name = "is_adult")
	private Boolean isAdult;
	
	@NotNull
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Column(name = "update_date")
	private Date updatedDate; 
	
	@ManyToOne(optional=false)
	private Author author;
	
	@URL
	@Column(name = "url_cover")
	private String urlCover;
	
	@ManyToOne(optional=true)
	//Once a history is published a moderator MUST be assigned
	private Moderator moderator;


}
