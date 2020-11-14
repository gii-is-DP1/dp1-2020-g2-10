package org.springframework.samples.petclinic.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Historia extends BaseEntity{
	
	// Atributos
	
	@NotEmpty
	private String titulo;
	
	@Length(min=120)
	@NotEmpty
	private String descripcion;
	
	private String dedicatoria;
	
	@NotNull
	private Genero genero;
//	COMENTADO PORQUE SI SE DEJA PUESTO PETA
//	@NotNull
//	@UniqueElements
//	private Collection<Etiqueta> etiquetas;
	
	@NotNull
	private EstadoHistoria estado;
	
	@NotNull
	private Boolean esAdulto;
	
	@NotNull
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private Date fechaActualizado; 
	
	// Relaciones
	@ManyToOne(optional=false)
	private Autor autor;

}
