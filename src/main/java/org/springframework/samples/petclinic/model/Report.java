package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "reports")
public @Data class Report extends BaseEntity{

	@NotNull
	private ReportType reportType;
	
	@NotNull
	private ReportStatus reportStatus;
	
	@NotNull
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDate date;
	
	@NotEmpty
	private String text;
	
	@NotNull
	@ManyToOne(optional=true)
	private Chapter chapter;
	
}
