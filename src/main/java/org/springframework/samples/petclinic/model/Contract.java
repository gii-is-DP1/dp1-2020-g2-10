package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "contracts")
public class Contract extends BaseEntity {

// Atributos
	
@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "offer_date")
private Date offerDate;
	
@NotBlank
@Column(columnDefinition = "TEXT")
private String header;

@NotBlank
@Column(columnDefinition = "TEXT")
private String text;

@NotNull
@Digits(fraction=2, integer = 3)
//@Column(columnDefinition = "NUMBER")
private Double remuneration;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "answer_date")
private Date answerDate;

@NotNull
@Enumerated(EnumType.STRING)
private ContractStatus contractStatus;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
@Column(name = "start_date")
private Date startDate;

@NotNull
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Future
@Column(name = "end_date")
private Date endDate;

@NotEmpty
@Column(name = "is_exclusive")
private Boolean isExclusive;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
@JoinColumn(name = "company_id")
private Company company;

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

public Author getAuthor() {
	return author;
}

//Método ToString

@Override
public String toString() {
	return "Contract [offerDate=" + offerDate + ", header=" + header + ", text=" + text + ", remuneration="
			+ remuneration + ", answerDate=" + answerDate + ", ContractStatus=" + contractStatus + ", startDate="
			+ startDate + ", endDate=" + endDate + ", isExclusive=" + isExclusive + ", author=" + author + "]";
}


}
