package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Author;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.mock;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthorService;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers= {ContractController.class, AlexandriaErrorController.class, AlexandriaControllerAdvice.class},
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class ContractControllerTests {
	
	private static final int TEST_CONTRACT_ID = 1;
	
	@Autowired
	private ContractController contractController;
	
	@MockBean
	private ContractService contractService;
	
	@MockBean
	private AuthorService authorService;
	
	@MockBean
	private CompanyService companyService;
	
	@Autowired
    private AlexandriaErrorController alexandriaErrorController;

    @Autowired
    private AlexandriaControllerAdvice alexandriaControllerAdvice;

    @MockBean
    private UserService userService;
    
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		Author autor1 = mock(Author.class);
		Author autor2 = mock(Author.class);
		Company company1 = mock(Company.class);
		
		Contract contrato1 = mock(Contract.class);
		contrato1.setId(1);
		contrato1.setCompany(company1);
		contrato1.setAuthor(autor1);
		
		Contract contrato2 = mock(Contract.class);
		contrato2.setId(2);
		contrato2.setCompany(company1);
		contrato2.setAuthor(autor2);
		
		List<Contract> contratosMismaCompany = new ArrayList<Contract>();
		contratosMismaCompany.add(contrato1);
		contratosMismaCompany.add(contrato2);
		
		given(this.contractService.findContractsByCompanyId()).willReturn(contratosMismaCompany);
		given(this.contractService.findContractById(TEST_CONTRACT_ID)).willReturn(contrato1);

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


}
