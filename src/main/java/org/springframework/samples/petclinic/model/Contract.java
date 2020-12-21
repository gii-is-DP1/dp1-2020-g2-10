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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "contracts")
public @Data class Contract extends BaseEntity {

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
private String body;

@NotNull
@Digits(fraction=2, integer = 8)
//@Column(columnDefinition = "NUMBER")
private Double remuneration;

@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "answer_date")
private Date answerDate;

@NotNull
@Enumerated(EnumType.STRING)
@Column(name = "contract_status")
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

@NotNull
@Column(name = "is_exclusive")
private Boolean isExclusive;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
private Author author;

@ManyToOne(optional=false)
@JoinColumn(name = "company_id")
private Company company;


}
