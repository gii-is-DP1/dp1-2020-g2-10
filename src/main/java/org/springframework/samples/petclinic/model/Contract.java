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
@Table(name = "contracts")
public class Contract extends BaseEntity {

// Atributos
	
@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
private Date offerDate;
	
@NotBlank
private String header;

@NotBlank
private String text;

@NotNull
@Digits(fraction=2, integer = 3)
private Double remuneration;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
private Date answerDate;

@NotNull
private ContractStatus contractStatus;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
private Date startDate;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
private Date endDate;

@NotEmpty
private Boolean isExclusive;

//Se tiene que añadir cuando esté creada la entidad COMPANY

//@ManyToOne(optional=false)
////@JoinColumn(name = "company_id")
//private Company company;


// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "autor_id")
private Autor author;

//Getters and setters

public String getHeader() {
	return header;
}

public void setHeader(String header) {
	this.header = header;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public Double getRemuneration() {
	return remuneration;
}

public void setRemuneration(Double remuneration) {
	this.remuneration = remuneration;
}

public ContractStatus getContractStatus() {
	return contractStatus;
}

public void setContractStatus(ContractStatus contractStatus) {
	this.contractStatus = contractStatus;
}

public Date getOfferDate() {
	return offerDate;
}

public Date getAnswerDate() {
	return answerDate;
}

public Date getStartDate() {
	return startDate;
}

public Date getEndDate() {
	return endDate;
}

public Boolean getEsExclusivo() {
	return isExclusive;
}

public Autor getAuthor() {
	return author;
}

//Método ToString

@Override
public String toString() {
	return "Contract [offerDate=" + offerDate + ", header=" + header + ", text=" + text + ", remuneration="
			+ remuneration + ", answerDate=" + answerDate + ", ContractState=" + contractStatus + ", startDate="
			+ startDate + ", endDate=" + endDate + ", esExclusivo=" + isExclusive + ", author=" + author + "]";
}


}
