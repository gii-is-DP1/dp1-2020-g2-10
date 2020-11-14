package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
//COMENTADO PORQUE SI SE DEJA PUESTO PETA (falta la relacion)	
//	@NotNull
//	private Capitulo capitulo;
//	
}
