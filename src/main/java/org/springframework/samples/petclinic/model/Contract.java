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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "contracts")
public class Contract extends BaseEntity {

// Atributos
	
@NotNull
@PastOrPresent
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "offer_date")
@Getter @Setter
private Date offerDate;
	
@NotBlank
@Column(columnDefinition = "TEXT")
@Getter @Setter
private String header;

@NotBlank
@Column(columnDefinition = "TEXT")
@Getter @Setter
private String body;

@NotNull
@Digits(fraction=2, integer = 8)
//@Column(columnDefinition = "NUMBER")
@Getter @Setter
private Double remuneration;


@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "answer_date")
@Getter @Setter
private Date answerDate;

@NotNull
@Enumerated(EnumType.STRING)
@Column(name = "contract_status")
@Getter @Setter
private ContractStatus contractStatus;

@NotNull
@Future
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "start_date")
@Getter @Setter
private Date startDate;

@NotNull
@Future
@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
@Column(name = "end_date")
@Getter @Setter
private Date endDate;

@NotNull
@Column(name = "is_exclusive")
@Getter @Setter
private Boolean isExclusive;

// Relaciones

@ManyToOne(optional=false)
@JoinColumn(name = "author_id")
@Getter @Setter
private Author author;

@ManyToOne(optional=false)
@JoinColumn(name = "company_id")
@Getter @Setter
private Company company;


}
