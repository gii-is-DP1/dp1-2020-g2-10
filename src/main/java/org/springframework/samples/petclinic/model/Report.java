package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "reports")
public @Data class Report extends BaseEntity{

	@NotNull
	private ReportType reportType;
	
	
	private ReportStatus reportStatus;
	
	
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDate date;
	
	@NotEmpty
	private String text;
	
	
	@ManyToOne(optional=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Chapter chapter;
	
}
