package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "contratos")
public class Contrato extends BaseEntity {

// Atributos
	
@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
private Date fechaOferta;
	
@NotBlank
private String encabezado;

@NotBlank
private String texto;

@NotNull
@Digits(fraction=2, integer = 3)
private Double remuneracion;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
private Date fechaRespuesta;

@NotNull
private estadoContrato estadoContrato;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
private Date fechaInicio;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
private Date fechaFin;

@NotEmpty
private Boolean esExclusivo;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "autor_id")
private Autor autor;

//Se tiene que añadir cuando esté creada la entidad EDITORIAL

//@ManyToOne(optional=false)
//@JoinColumn(name = "editorial_id")
//private Editorial editorial;

//Getters and setters

public String getEncabezado() {
	return encabezado;
}

public void setEncabezado(String encabezado) {
	this.encabezado = encabezado;
}

public String getTexto() {
	return texto;
}

public void setTexto(String texto) {
	this.texto = texto;
}

public Double getRemuneracion() {
	return remuneracion;
}

public void setRemuneracion(Double remuneracion) {
	this.remuneracion = remuneracion;
}

public Date getFechaRespuesta() {
	return fechaRespuesta;
}

public void setFechaRespuesta(Date fechaRespuesta) {
	this.fechaRespuesta = fechaRespuesta;
}

public estadoContrato getEstadoContrato() {
	return estadoContrato;
}

public void setEstadoContrato(estadoContrato estadoContrato) {
	this.estadoContrato = estadoContrato;
}

public Date getFechaOferta() {
	return fechaOferta;
}

public Date getFechaInicio() {
	return fechaInicio;
}

public Date getFechaFin() {
	return fechaFin;
}

public Boolean getEsExclusivo() {
	return esExclusivo;
}

public Autor getAutor() {
	return autor;
}

//Método ToString

@Override
public String toString() {
	return "Contrato [fechaOferta=" + fechaOferta + ", encabezado=" + encabezado + ", texto=" + texto
			+ ", remuneracion=" + remuneracion + ", fechaRespuesta=" + fechaRespuesta + ", estadoContrato="
			+ estadoContrato + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", esExclusivo="
			+ esExclusivo + ", autor=" + autor + "]";
}


}
