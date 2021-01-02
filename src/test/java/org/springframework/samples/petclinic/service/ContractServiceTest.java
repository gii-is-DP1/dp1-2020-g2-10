package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
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


// HU8+E1 - Envío del contrato relleno con éxito

@SuppressWarnings("deprecation")
@Test
@Transactional
@WithMockUser(value = "company1", authorities = {
        "company"
    })
public void shouldInsertContract() {
		 
		Contract contract = new Contract();
		
		Boolean b=false;
		
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
		contract.setIsExclusive(b.TRUE);
		contract.setOfferDate(moment3);
		contract.setRemuneration(5.67);
		contract.setStartDate(moment);
		
		Author a = new Author();
		contract.setAuthor(a);
			
		Company c = new Company();
		contract.setCompany(c);
		
		when(contractRepository.save(contract)).thenReturn(contract);
		
		this.contractService.saveContract(contract);
				
		assertThat(contract.getId()).isNotNull();
		
}


//Escenario Negativo 

@Test
@Transactional
@WithMockUser(value = "company1", authorities = {
        "company"
    })
public void shouldInsertContractEmpty() {
	Contract contract = new Contract();
	

	when(contractRepository.save(contract)).thenReturn(contract);
	
	this.contractService.saveContract(contract);

	assertThat(contract.getId()).isNull();
	assertThat(contract.getBody()).isNull();
	assertThat(contract.getHeader()).isNull();
	assertThat(contract.getAnswerDate()).isNull();





}

}
