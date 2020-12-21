package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Contract;
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

	public ContractService(ContractRepository contractRepository, AuthorService authorService,
			CompanyService companyService) {
		super();
		this.contractRepository = contractRepository;
		this.authorService = authorService;
		this.companyService = companyService;
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
