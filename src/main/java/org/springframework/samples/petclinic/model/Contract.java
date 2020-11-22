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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contracts")
@Getter
@Setter
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

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
//@JoinColumn(name = "company_id")
private Company company;


//Método ToString

@Override
public String toString() {
	return "Contract [offerDate=" + offerDate + ", header=" + header + ", text=" + text + ", remuneration="
			+ remuneration + ", answerDate=" + answerDate + ", ContractStatus=" + contractStatus + ", startDate="
			+ startDate + ", endDate=" + endDate + ", isExclusive=" + isExclusive + ", author=" + author + "]";
}


}
