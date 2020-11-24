package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "reports")
public @Data class Report extends BaseEntity{

	@NotEmpty
	private ReportType reportType;
	
	@NotEmpty
	private ReportStatus reportStatus;
	
	@NotEmpty
	@FutureOrPresent
	private LocalDate date;
	
	@NotEmpty
	private String text;
	
	@NotEmpty
	@ManyToOne(optional=true)
	private Chapter chapter;
	
}
