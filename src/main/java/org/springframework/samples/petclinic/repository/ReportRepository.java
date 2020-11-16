package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Report;

public interface ReportRepository extends CrudRepository<Report, Integer>{

}
