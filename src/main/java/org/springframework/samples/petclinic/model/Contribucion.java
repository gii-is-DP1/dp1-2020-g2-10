package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contribuciones")
public class Contribucion extends BaseEntity {

// Entidades

@NotNull
private tipoContribucion tipoCopntribucion;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "historia_id")
private Historia historia;

@ManyToOne(optional=false)
@JoinColumn(name = "autor_id")
private Autor autor;

//Getters and setters

public tipoContribucion getTipoCopntribucion() {
	return tipoCopntribucion;
}

public void setTipoCopntribucion(tipoContribucion tipoCopntribucion) {
	this.tipoCopntribucion = tipoCopntribucion;
}

public Historia getHistoria() {
	return historia;
}

public void setHistoria(Historia historia) {
	this.historia = historia;
}

public Autor getAutor() {
	return autor;
}

@Override
public String toString() {
	return "Contribucion [tipoCopntribucion=" + tipoCopntribucion + ", historia=" + historia + ", autor=" + autor + "]";
}


}
