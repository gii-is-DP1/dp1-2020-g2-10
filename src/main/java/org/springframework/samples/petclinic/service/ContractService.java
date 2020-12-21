package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Author;
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
