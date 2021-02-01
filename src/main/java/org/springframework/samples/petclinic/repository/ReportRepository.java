package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Report;

public interface ReportRepository extends CrudRepository<Report, Integer>{
	
	// Recogemos los reportes emitidos al capítulo correspondiente al id introducido en la consulta
		@Query("SELECT report FROM Report report WHERE report.chapter.id  =:id")
		public Collection<Report> findReportByChapterId(@Param("id") int id);
		
		@Query("SELECT report FROM Report report WHERE report.id  =:id")
		public Report findReportById(@Param("id") int id);

}
