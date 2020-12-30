package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContractService {

	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private CompanyService companyService;
	
	
	@Transactional(readOnly = true)
	private Collection<Contract> findByAuthorAndStatus(Author author, ContractStatus status){
		return contractRepository.findByAuthorIdAndContractStatus(author.getId(), status);
	}
	
	@Transactional(readOnly = true)
	public Collection<Contract> findByAuthorPrincipalAndStatus(ContractStatus status){
		Author principalAuthor = authorService.getPrincipal(); 
		
		return findByAuthorAndStatus(principalAuthor, status);
	}
	
	//COMPANY
	@Transactional(readOnly = true)	//LIST
	public Collection<Contract> findContractsByCompanyId() throws DataAccessException {
		//Buscamos los contratos segun la id de la company logeada
		Company principalCompany = this.companyService.getPrincipal();
		Collection<Contract> contratos = contractRepository.findContractsByCompanyId(principalCompany.getId());
		return contratos;
	}	

	@Transactional //SHOW
	public Contract findContractById(Integer contractId) throws DataAccessException {
		Contract contract = contractRepository.findById(contractId).get();
		Company principalCompany = this.companyService.getPrincipal();
		Company contractCompany = contract.getCompany();
		
		assertTrue("Only the creative company can see this contract",
				principalCompany.equals(contractCompany));
		return contract;
	}	
	//-------------------
	public Contract createContract(){
		Contract res = new Contract();
		
		
		res.setAuthor(authorService.getPrincipal());
		res.setCompany(companyService.getPrincipal());
		
		return res;
	}
	
	@Transactional
	public void saveContract(Contract contract){
		contractRepository.save(contract);		
		
	}


}
