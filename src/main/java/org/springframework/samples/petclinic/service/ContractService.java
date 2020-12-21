package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractService {

	private ContractRepository contractRepository;
	
	private CompanyService companyService;
	
	@Autowired
	public ContractService(ContractRepository contractRepository, CompanyService companyService) {
		this.contractRepository = contractRepository;
		this.companyService = companyService;
	}	
	
	@Transactional(readOnly = true)	
	public Collection<Contract> findContractsByCompanyId(Integer companyId) throws DataAccessException {
		return contractRepository.findContractsByCompanyId(companyId);
	}	

	@Transactional
	public Contract findContractById(Integer contractId) throws DataAccessException {
		Contract contract = contractRepository.findById(contractId).get();
		return contract;
	}	

}

