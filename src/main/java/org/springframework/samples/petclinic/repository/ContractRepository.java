package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;

public interface ContractRepository extends CrudRepository<Contract, Integer>{
	
	/*Devuelve una lista de contratos dado un autor y opcionalmente se especifica un ContractStatus
	 * si el ContractStatus es NULL, no se tiene en cuenta el criterio de estado y se recuoperan todos los contratos*/
	@Query("SELECT DISTINCT contract FROM Contract contract where contract.author.id = :authorId AND (:status is null OR  contract.contractStatus = :status)")
	public Collection<Contract> findByAuthorIdAndContractStatus(@Param("authorId") int authorId, @Param("status") ContractStatus status);
	
	/*Devuelve una lista de contratos dado una empresa y opcionalmente se especifica un ContractStatus
	 * si el ContractStatus es NULL, no se tiene en cuenta el criterio de estado y se recuoperan todos los contratos*/
	@Query("SELECT DISTINCT contract FROM Contract contract where contract.company.id = :companyId AND (:status is null OR  contract.contractStatus = :status)")
	public Collection<Contract> findByCompanyIdAndContractStatus(@Param("companyId") int companyId, @Param("status") ContractStatus status);


	@Query("SELECT contract FROM Contract contract WHERE contract.company.id  =:id")
	public Collection<Contract> findContractsByCompanyId(@Param("id") int id);

}
