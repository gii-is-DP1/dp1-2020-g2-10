package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@EqualsAndHashCode(callSuper = false)
public @Data class Chapter extends BaseEntity{

	@NotEmpty
	@Positive
	private Integer index;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String text;
	
	@NotEmpty
	private Boolean isPublished;
	
	@ManyToOne(optional=false)
	private Story story;
	
}
