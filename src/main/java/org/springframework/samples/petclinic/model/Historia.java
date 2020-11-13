package org.springframework.samples.petclinic.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class Historia extends BaseEntity{
	
	@NotEmpty
	private String titulo;
	
	@Length(min=120)
	@NotEmpty
	private String descripcion;
	
	private String dedicatoria;

}
