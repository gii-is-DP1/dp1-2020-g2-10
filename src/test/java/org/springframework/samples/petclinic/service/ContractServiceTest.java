package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.repository.AuthorRepository;
import org.springframework.samples.petclinic.repository.CompanyRepository;
import org.springframework.samples.petclinic.repository.ContractRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

@Mock
private ContractRepository contractRepository;

protected ContractService contractService;

@Mock
private AuthorRepository authorRepository;

protected AuthorService authorService;

@Mock
private CompanyRepository companyRepository;

protected CompanyService companyService;


@BeforeEach
void setup() {
	contractService = new ContractService(contractRepository,authorService,companyService);
}

//Escenario Positivo:


// Test HU8+E1 - Envío del contrato relleno con éxito

@SuppressWarnings("deprecation")
@Test
@WithMockUser(value = "company1", authorities = {
        "company"
    })
@Transactional
public void shouldInsertContract() {

		 
		Contract contract = new Contract();
		
		Date moment; 
		
		Date moment2;
		
		Date moment3;
		
        moment = new Date(System.currentTimeMillis() - 1);
        moment2 = new Date(2022, 06, 30, 23, 59, 00);
        moment3 = new Date(2023, 05, 30, 23, 59, 00);

        
		contract.setId(1);
		contract.setAnswerDate(moment);
		contract.setBody("Contrato DP");
		contract.setContractStatus(ContractStatus.PENDING);
		contract.setEndDate(moment2);
		
		contract.setHeader("Contrato milenario");
		contract.setIsExclusive(true);
		contract.setOfferDate(moment3);
		contract.setRemuneration(5.67);
		contract.setStartDate(moment);
		
		Author a = new Author();
		contract.setAuthor(a);
			
		Company c = new Company();
		contract.setCompany(c);
		
		when(contractRepository.save(contract)).thenReturn(contract);
		
		this.contractService.saveContract(contract);
		
		verify(contractRepository).save(contract);
				
		assertThat(contract.getId()).isNotNull();
		
}


//Escenario Negativo 


@Test
@WithMockUser(value = "company1", authorities = {
        "company"
    })
@Transactional
public void shouldInsertContractEmpty() {
	
	Contract contract = new Contract();
	
	contract.setId(null);
	contract.setAnswerDate(null);
	contract.setBody(null);
	contract.setEndDate(null);
	
	contract.setHeader(null);
	contract.setOfferDate(null);
	
	
	when(contractRepository.save(contract)).thenReturn(contract);
	this.contractService.saveContract(contract);
	
	assertThat(contract.getId()).isNull();
	assertThat(contract.getBody()).isNull();
	assertThat(contract.getHeader()).isNull();
	assertThat(contract.getAnswerDate()).isNull();
	assertThat(contract.getContractStatus()).isNull();

	
	verify(contractRepository).save(contract);


}



//Escenario Positivo:


//Test HU10+E1 - Aceptar o rechazar contrato

@SuppressWarnings("deprecation")
@Test
@WithMockUser(value = "author1", authorities = {
        "author"
    })
@Transactional
public void shouldAcceptOrRejectContract() {
	
	//Primer contrato con ContractStatus igual a ACCEPTED

	Contract contract = new Contract();
	
	Date moment; 
	
	Date moment2;
	
	Date moment3;
	
    moment = new Date(System.currentTimeMillis() - 1);
    moment2 = new Date(2022, 06, 30, 23, 59, 00);
    moment3 = new Date(2023, 05, 30, 23, 59, 00);

    
	contract.setId(1);
	contract.setAnswerDate(moment);
	contract.setBody("Contrato DP");
	contract.setContractStatus(ContractStatus.ACCEPTED);
	contract.setEndDate(moment2);
	
	contract.setHeader("Contrato milenario");
	contract.setIsExclusive(true);
	contract.setOfferDate(moment3);
	contract.setRemuneration(5.67);
	contract.setStartDate(moment);
	
	Author a = new Author();
	contract.setAuthor(a);
		
	Company c = new Company();
	contract.setCompany(c);
	
	//Segundo contrato con ContractStatus igual a Rejected
	
	Contract contract2 = new Contract();
	
	
    moment = new Date(System.currentTimeMillis() - 1);
    moment2 = new Date(2022, 06, 30, 23, 59, 00);
    moment3 = new Date(2023, 05, 30, 23, 59, 00);

    
    contract2.setId(1);
    contract2.setAnswerDate(moment);
    contract2.setBody("Contrato DP");
    contract2.setContractStatus(ContractStatus.REJECTED);
    contract2.setEndDate(moment2);
	
    contract2.setHeader("Contrato milenario");
    contract2.setIsExclusive(true);
    contract2.setOfferDate(moment3);
    contract2.setRemuneration(5.67);
    contract2.setStartDate(moment);
		
	Company co = new Company();
	contract2.setCompany(co);
	
	//Tercer contrato con ContractStatus igual a PENDING
	
		Contract contract3 = new Contract();
		
	    moment = new Date(System.currentTimeMillis() - 1);
	    moment2 = new Date(2022, 06, 30, 23, 59, 00);
	    moment3 = new Date(2023, 05, 30, 23, 59, 00);

	    
	    contract3.setId(1);
	    contract3.setAnswerDate(moment);
	    contract3.setBody("Contrato DP");
	    contract3.setContractStatus(ContractStatus.PENDING);
	    contract3.setEndDate(moment2);
		
	    contract3.setHeader("Contrato milenario");
	    contract3.setIsExclusive(true);
	    contract3.setOfferDate(moment3);
	    contract3.setRemuneration(5.67);
	    contract3.setStartDate(moment);
			
		Company con = new Company();
		contract3.setCompany(con);
	
	if (contract.getContractStatus().equals(ContractStatus.ACCEPTED)) {
		
		Author a2 = new Author();
		contract.setAuthor(a2);
		
		when(contractRepository.save(contract)).thenReturn(contract);
 
		this.contractService.saveContract(contract);

		
	}else if (contract.getContractStatus().equals(ContractStatus.REJECTED)) {
		
		assertThat(contract.getAuthor()).isNull();

		
	} else {
		
		assertThat(contract.getAuthor()).isNull();

	}


}

//No hay escenario negativo, al tener que aplicar una nueva regla de negocio, 

}
