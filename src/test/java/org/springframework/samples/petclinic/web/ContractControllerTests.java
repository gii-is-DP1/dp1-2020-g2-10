package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.model.ContractStatus;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers= {ContractController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class ContractControllerTests {

	private static final int TEST_CONTRACT_ID = 1;
	
	private static final int TEST_COMPANY_ID = 1;
	
	private static final int TEST_AUTHOR_ID = 1;

	@Autowired
	private ContractController contractController;
	
	@MockBean
	private ContractService contractService;
	
	@MockBean

	private CompanyService companyService;
	
	@MockBean
	private AuthorService authorService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private AlexandriaErrorController alexandriaErrorController;

    @Autowired
    private AlexandriaControllerAdvice alexandriaControllerAdvice;

    
    @MockBean
    private UserService userService;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		
		Date moment; 
		
		Date moment2;
		
		Date moment3;
		
        moment = new Date(System.currentTimeMillis() - 1);
        moment2 = new Date(2022, 06, 30, 23, 59, 00);
        moment3 = new Date(2023, 05, 30, 23, 59, 00);
		
		Author autor1 = mock(Author.class);
		Author autor2 = mock(Author.class);
		Company company1 = mock(Company.class);
		
		Contract contrato1 = mock(Contract.class);
		contrato1.setId(1);
		contrato1.setCompany(company1);
		
		autor1.setId(TEST_AUTHOR_ID);
		
		contrato1.setAuthor(autor1);
		contrato1.setAnswerDate(moment);
		contrato1.setBody("Contrato DP");
		contrato1.setContractStatus(ContractStatus.ACCEPTED);
		contrato1.setEndDate(moment2);
		
		contrato1.setHeader("Contrato milenario");
		contrato1.setIsExclusive(true);
		contrato1.setOfferDate(moment3);
		contrato1.setRemuneration(5.67);
		contrato1.setStartDate(moment);

		Contract contrato2 = mock(Contract.class);
		contrato2.setId(2);
		contrato2.setCompany(company1);
		contrato2.setAuthor(autor2);
		
		List<Contract> contratosMismaCompany = new ArrayList<Contract>();
		contratosMismaCompany.add(contrato1);
		contratosMismaCompany.add(contrato2);
		
		Company comp = new Company();
		comp.setId(TEST_COMPANY_ID);
		Optional<Company> opCom =Optional.of(comp);
		contrato1.setCompany(comp);
		
		given(this.contractService.findContractsByCompanyId()).willReturn(contratosMismaCompany);
		given(this.contractService.findContractById(TEST_CONTRACT_ID)).willReturn(contrato1);
		given(this.companyService.findCompanyById(TEST_COMPANY_ID)).willReturn(opCom);
		given(this.companyService.getPrincipal()).willReturn(new Company());
		given(this.authorService.findAuthorById(TEST_AUTHOR_ID)).willReturn(autor1);

	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListContractsOfCompany() throws Exception {
		mockMvc.perform(get("/contracts/company/list"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("contracts"))
				.andExpect(model().size(2))
				.andExpect(view().name("contracts/listContracts"));
	}

	
	@WithMockUser(value = "spring")
	@Test
	void testShowContractOfCompany() throws Exception {
	    mockMvc.perform(get("/contracts/{contractId}/show", TEST_CONTRACT_ID)).andExpect(status().isOk())
	            .andExpect(model().attributeExists("contract")).andExpect(view().name("contracts/showContracts"));
	}

	
	//Pruebas de controlador HU-08
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationContractForm() throws Exception {
		mockMvc.perform(get("/contracts/new")).andExpect(status().isOk())
				.andExpect(view().name("contracts/createContractForm"));
	}

	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationContractFormSuccess() throws Exception {
		mockMvc.perform(post("/contracts/new")
							.with(csrf())
							.param("id", "1")
							.param("offerDate", "2020/05/30 23:59")
							.param("header", "Contrato milenario")
							.param("body", "Contrato DP")
							.param("remuneration", "5.67")
							.param("answerDate", "2021/05/06 20:52")
							.param("contractStatus", "ACCEPTED")
							.param("startDate", "2021/06/09 20:52")
							.param("endDate", "2022/06/30 23:59")
							.param("isExclusive", "true")
							.param("author.id","1")
							.param("company.id", "1"))
				.andExpect(status().is3xxRedirection())
			//  .andExpect(model().attributeHasErrors("contract"))
				.andExpect(view().name("redirect:/contracts/list"));
	}

	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationContractFormHasErrors() throws Exception {
		mockMvc.perform(post("/contracts/new")
							.with(csrf())
							.param("id", "1")
							.param("body", "")
							.param("contractStatus", "REJECTED")
							.param("header", "")
							.param("isExclusive", "true")
							.param("startDate", "")
							.param("author.id","1"))
				.andExpect(model().attributeHasErrors("contract"))
				.andExpect(status().isOk())
				.andExpect(view().name("contracts/createContractForm"));
	}
	
	//Pruebas de controlador HU-10
	

	@WithMockUser(value = "spring")
    @Test
	void testProcessAcceptOrRejectContractFormSuccess() throws Exception {
		
		mockMvc.perform(get("/contracts/{contractId}/accept", TEST_CONTRACT_ID)
				.with(csrf())
				.param("id", "1")
				.param("contractStatus", "ACCEPTED"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/contracts/list"));
		
		mockMvc.perform(get("/contracts/{contractId}/reject", TEST_CONTRACT_ID)
				.with(csrf())
				.param("id", "1")
				.param("contractStatus", "REJECTED"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/contracts/list"));
		
	}
	


	
	

}
