package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Contract;

public interface ContractRepository extends CrudRepository<Contract, Integer>{

	@Query("SELECT contract FROM Contract contract WHERE contract.company.id  =:id")
	public Collection<Contract> findContractsByCompanyId(@Param("id") int id);
			
}
