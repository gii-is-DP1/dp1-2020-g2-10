package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.samples.petclinic.service.exceptions.AuthorIdNullException;
import org.springframework.samples.petclinic.service.exceptions.EndDateBeforeStartDateException;
import org.springframework.samples.petclinic.service.exceptions.contract.ExclusivityContractConflict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	private Collection<Contract> findAcceptedByAuthorAndDatesAndExclusivity(Author author, Date queryStartDate, Date queryEndDate, Boolean isExclusive){
		return contractRepository.findAcceptedByAuthorAndDateAndExclusivity(author.getId(), queryStartDate, queryEndDate, isExclusive);
	}
	
	public Collection<Contract> findConflictingContractsByPrincipalAuthor(Author author, Contract contract){
		// Query parameters
		Author principal = authorService.getPrincipal();
		Boolean isExclusiveQuery = contract.getIsExclusive()? null: true;
			/* Si el nuevo contrato:
			 	* es exclusivo -> cualquier contrato causará conflicto (null como parametro de la query)
			 	* no es exclusivo -> solo causarían conflicto los contratos los contratos EXCLUSIVOS
			 * */
		return findAcceptedByAuthorAndDatesAndExclusivity(principal, contract.getStartDate(), contract.getEndDate(), isExclusiveQuery);
	}
	
	public Collection<Contract> findByPrincipalAndStatus(ContractStatus status){
		String principalRole = userService.getPrincipalRole();
		
		Collection<Contract> res;
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
	
	
		@Transactional(readOnly = true)	//LIST
		public Collection<Contract> findContractsByAuthorId() throws DataAccessException {
			//Buscamos los contratos segun la id de la company logeada
			Author principalAuthor = this.authorService.getPrincipal();
			Collection<Contract> contratos = contractRepository.findContractsByAuthorId(principalAuthor.getId());
			return contratos;
		}	

	public Contract findContractById(Integer contractId) throws DataAccessException {
		System.out.println("contractService.findContractById(): " + contractId);
		Optional<Contract> optContract = contractRepository.findById(contractId);
		// TODO: Los Autores tambien pueden ver los contratos, corregir para compañia y autor
//		Company principalCompany = this.companyService.getPrincipal();
//		Company contractCompany = contract.getCompany();
//		
//		assertTrue("Only the creative company can see this contract",
//				principalCompany.equals(contractCompany));
		return optContract.isPresent()?optContract.get():null;
	}	
	//-------------------
	public Contract createContract(){
		Contract res = new Contract();
		
		res.setCompany(companyService.getPrincipal());

		Date moment;
		
        moment = new Date(System.currentTimeMillis() - 1);
        res.setOfferDate(moment);
        
		res.setContractStatus(ContractStatus.PENDING);

		return res;
	}
	
	@Transactional 
	public void saveContract(@Valid Contract contract) throws DataAccessException {
	
		contractRepository.save(contract);		
	        	
	}
	
	@Transactional
	public void answerContract(Integer contractId, ContractStatus updatedStatus) throws DataAccessException, ExclusivityContractConflict{
		assertTrue("Only an author can respond to contracts.", authorService.isPrincipalAuthor());
		
		Author principal = authorService.getPrincipal();
		Contract originalContract = findContractById(contractId);
		assertTrue("Only the author recipient of the contract can provide an answer to the contract.",
				originalContract.getAuthor().equals(principal));
		
		assertTrue("The contract has already been responded, no further modifications are allowed.", 
				originalContract.getContractStatus().equals(ContractStatus.PENDING));
		
		assertTrue("The contract cannot longer be responded as its start date has already passed.", 
				originalContract.getStartDate().after(new Date()));
		
		if(updatedStatus.equals(ContractStatus.ACCEPTED)) {
			Collection<Contract> currentConflictingContracts = findConflictingContractsByPrincipalAuthor(principal, originalContract);
			log.info("=========== Conflicting contracts ============ \n" 
					+ currentConflictingContracts.stream().map(x -> "Title:" + x.getHeader() + ", Exclusive:" + x.getIsExclusive() + ", startDate:" + x.getStartDate() + ", endDate:" + x.getEndDate() + "\n").collect(Collectors.joining()));
			if(!currentConflictingContracts.isEmpty()) {
				throw new ExclusivityContractConflict(String.format("The Contract '%s' "
						+ "contractId=%d cannot be accepted as there is conflicts for exclusivity reasons",
						originalContract.getHeader(), contractId));
			}
		}
		
		originalContract.setContractStatus(updatedStatus);
		originalContract.setAnswerDate(new Date());
		
		saveContract(originalContract);
	}


}
