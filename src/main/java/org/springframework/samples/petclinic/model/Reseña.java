package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reseñas")
public class Reseña extends BaseEntity {

// Atributos
	
@NotEmpty
private String titulo;

@NotNull
@Range(min=0, max=5)
private Integer puntuacion;

@NotEmpty
private String text;

@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
private Date fechaPublicacion;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "autor_id")
private Autor autor;

@ManyToOne(optional=false)
@JoinColumn(name = "historia_id")
private Historia historia;

// Getters ands setters

public Integer getPuntuacion() {
	return puntuacion;
}

public void setPuntuacion(Integer puntuacion) {
	this.puntuacion = puntuacion;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Historia getHistoria() {
	return historia;
}

public void setHistoria(Historia historia) {
	this.historia = historia;
}

public String getTitulo() {
	return titulo;
}

public Date getFechaPublicacion() {
	return fechaPublicacion;
}

public Autor getAutor() {
	return autor;
}

//Método ToString

@Override
public String toString() {
	return "Reseña [titulo=" + titulo + ", puntuacion=" + puntuacion + ", text=" + text + ", fechaPublicacion="
			+ fechaPublicacion + ", autor=" + autor + ", historia=" + historia + "]";
}


}
