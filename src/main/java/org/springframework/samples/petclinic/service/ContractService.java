package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.model.User;
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
	
	@Autowired
	private UserService userService;
	
	
	public ContractService(ContractRepository contractRepository, AuthorService authorService,
			CompanyService companyService) {
		super();
		this.contractRepository = contractRepository;
		this.authorService = authorService;
		this.companyService = companyService;
	}

	
	@Transactional(readOnly = true)
	private Collection<Contract> findByAuthorAndStatus(Author author, ContractStatus status){
		return contractRepository.findByAuthorIdAndContractStatus(author.getId(), status);
	}
	
	@Transactional(readOnly = true)
	private Collection<Contract> findByCompanyAndStatus(Company company, ContractStatus status){
		return contractRepository.findByCompanyIdAndContractStatus(company.getId(), status);
	}
	
	@Transactional(readOnly = true)
	public Collection<Contract> findByAuthorPrincipalAndStatus(ContractStatus status){
		Author principalAuthor = authorService.getPrincipal();
		
		return findByAuthorAndStatus(principalAuthor, status);
	}
	
	@Transactional(readOnly = true)
	public Collection<Contract> findByCompanyPrincipalAndStatus(ContractStatus status){
		Company principalCompany = companyService.getPrincipal();
		
		return findByCompanyAndStatus(principalCompany, status);
	}
	
	
	
	public Collection<Contract> findByPrincipalAndStatus(ContractStatus status){
		String principalRole = userService.getPrincipalRole();
		
		Collection<Contract> res;
		System.out.println("-----------PrincipalRole is: " + principalRole);
		switch(principalRole){
		case "author":
			res = findByAuthorPrincipalAndStatus(status);
			break;
		case "company":
			res = findByCompanyPrincipalAndStatus(status);
			break;
		default:
			res = new ArrayList<Contract>();
			break;
		}
		
		return res;
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
		
		res.setCompany(companyService.getPrincipal());

		Date moment;
		
        moment = new Date(System.currentTimeMillis() - 1);
        res.setOfferDate(moment);
		return res;
	}
	
	@Transactional
	public void saveContract(@Valid Contract contract) throws DataAccessException{
		contractRepository.save(contract);		
		
	}


}
