package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Company;
import org.springframework.samples.petclinic.model.Contract;
import org.springframework.samples.petclinic.service.CompanyService;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = ContractController.class, excludeAutoConfiguration = SecurityConfiguration.class)
public class ContractControllerTests {

	private static final int TEST_CONTRACT_ID = 1;
	
	private static final int TEST_COMPANY_ID = 1;
	
	@Autowired
	private ContractController contractController;
	
	@MockBean
	private ContractService contractService;
	
	@MockBean
	private CompanyService companyService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setup() {
		Company comp = new Company();
		Optional<Company> opCom =Optional.of(comp);
		
		given(this.contractService.findContractById(TEST_CONTRACT_ID)).willReturn(new Contract());
		given(this.companyService.findCompanyById(TEST_COMPANY_ID)).willReturn( opCom);
	}
	
	
	@WithMockUser(value = "company1")
    @Test
	void testInitCreationContractForm() throws Exception {
		mockMvc.perform(get("/contracts/new")).andExpect(status().isOk())
				.andExpect(view().name("contracts/createContractForm")).andExpect(model().attributeExists("review"));
	}

	@WithMockUser(value = "company1")
    @Test
	void testProcessCreationContractFormSuccess() throws Exception {
		mockMvc.perform(post("/contracts/new")
							.with(csrf())
							.param("id", "1")
							.param("answerDate", "2021/02/01 20:52:00")
							.param("body", "Contrato DP")
							.param("contractStatus", "ACCEPTED")
							.param("endDate", "2022/06/30 23:59:00")
							.param("header", "Contrato milenario")
							.param("isExclusive", "true")
							.param("offerDate", "2023/05/30 23:59:00")
							.param("remuneration", "5.67")
							.param("startDate", "2021/02/01 20:52:00")
							.param("author.id","1")
							.param("company.id", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/contracts/list"));
	}

	@WithMockUser(value = "company1")
    @Test
	void testProcessCreationContractFormHasErrors() throws Exception {
		mockMvc.perform(post("/contracts/new")
							.with(csrf())
							.param("id", "1")
							.param("body", "Contrato DP")
							.param("contractStatus", "ACCEPTED")
							.param("header", "Contrato milenario")
							.param("isExclusive", "true")
							.param("startDate", "2021/02/01 20:52:00")
							.param("author.id","1"))
				.andExpect(model().attributeHasErrors("contract"))
				.andExpect(status().isOk())
				.andExpect(view().name("contracts/createContractForm"));
	}
	


}
