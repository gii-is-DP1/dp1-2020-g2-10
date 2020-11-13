package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;


@Entity
public class Capitulo extends BaseEntity{

	@NotEmpty
	@Positive
	private Integer indice;
	
	@NotEmpty
	private String titulo;
	
	@NotEmpty
	private String texto;
	
	@NotEmpty
	private Boolean estaPublicado;
	
	@ManyToOne(optional=false)
	private Historia historia;
	
}
