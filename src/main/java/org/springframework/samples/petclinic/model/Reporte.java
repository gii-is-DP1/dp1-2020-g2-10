package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

@Entity
public class Reporte extends BaseEntity{

	@NotEmpty
	private TipoReporte tipoReporte;
	
	@NotEmpty
	private EstadoReporte estadoReporte;
	
	@NotEmpty
	@FutureOrPresent
	private LocalDate fecha;
	
	@NotEmpty
	private String texto;
	
	@NotEmpty
	@ManyToOne(optional=true)
	private Capitulo capitulo;
	
}
